package com.codeaspect.sheldon.helpers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.codeaspect.sheldon.AuditChecker;
import com.codeaspect.sheldon.annonations.Auditable;
import com.codeaspect.sheldon.annonations.AuditableList;
import com.codeaspect.sheldon.converters.ListConverter;
import com.codeaspect.sheldon.entity.AuditChangeEntry;
import com.codeaspect.sheldon.entity.AuditPath;
import com.codeaspect.sheldon.exceptions.ConversionException;

public class AuditListHelper {

	private Field field;
	private AuditableList auditableList;

	public AuditListHelper(Field fld) {
		this.field = fld;
		this.auditableList = fld.getAnnotation(AuditableList.class);

		assert auditableList != null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<?> getSortedList(Object delegate) {
		try {
			Collection collection = (Collection) (new ObjectReflection(delegate)).getFieldValue(field);
			if (collection == null) {
				return new ArrayList();
			}

			ListConverter converter = auditableList.listConverter().newInstance();
			List<?> baseList = converter.convertToList(field, collection);
			Collections.sort(baseList, ComparatorHelper.getListComparator(field));

			return baseList;
		} catch (Exception e) {
			throw new ConversionException("Unable to create list", e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getMatch(Object item, List<?> list, int start) {
		Comparator comparator = ComparatorHelper.getListComparator(field);
		for (int idx = start; idx < list.size(); idx++) {
			if (comparator.compare(item, list.get(idx)) == 0) {
				return idx;
			}
		}
		return -1;
	}

	public List<AuditChangeEntry> checkList(Object o1, Object o2, AuditPath path) {
		List<AuditChangeEntry> auditChangeList = new ArrayList<AuditChangeEntry>();

		List<?> list1 = getSortedList(o1), list2 = getSortedList(o2);
		Map<Integer, Integer> listPositions = new HashMap<Integer, Integer>();

		String fieldDescription = (FieldMetadataHelper.getGenericType(field).getAnnotation(Auditable.class)).name();
		AuditableList auditableList = field.getAnnotation(AuditableList.class);
		
		int lastIndexMatched=0;
		for(int idx=0;idx<list1.size();idx++){
			Object item = list1.get(idx);
			int matchLoaction = getMatch(item, list2, lastIndexMatched);
			listPositions.put(idx, matchLoaction);
			if(matchLoaction>0){
				lastIndexMatched=matchLoaction;
			}
		}
		
		Set<Integer> existingElements = new HashSet<Integer>();
		for(int listIdx : listPositions.keySet()){
			int otherListIdx = listPositions.get(listIdx);
			if(otherListIdx==-1){//Deletion
				AuditPath auditPath = new AuditPath(path, field.getName(), fieldDescription, auditableList.groups());
				auditChangeList.add(AuditChangeEntry.deleteEntry(auditPath, list1.get(listIdx)));
			}else{//Modification
				existingElements.add(otherListIdx);
				Object secondListItem = list2.get(otherListIdx);
				AuditPath fieldPath = new AuditPath(path, field.getName(), fieldDescription, auditableList.groups());
				List<AuditChangeEntry> changes = (new AuditChecker<Object>().checkObjects(list1.get(listIdx), secondListItem, fieldPath));
				auditChangeList.addAll(changes);
			}
		}
		
		for(int secondIdx=0; secondIdx<list2.size(); secondIdx++){
			if(!existingElements.contains(secondIdx)){
				AuditPath fieldPath = new AuditPath(path, field.getName(), fieldDescription, auditableList.groups());
				auditChangeList.add(AuditChangeEntry.createEntry(fieldPath, list2.get(secondIdx)));
			}
		}
		
		return auditChangeList;

	}

}
