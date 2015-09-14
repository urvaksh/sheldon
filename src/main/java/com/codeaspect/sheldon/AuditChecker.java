package com.codeaspect.sheldon;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.codeaspect.sheldon.annonations.AuditField;
import com.codeaspect.sheldon.annonations.Auditable;
import com.codeaspect.sheldon.entity.AuditChangeEntry;
import com.codeaspect.sheldon.entity.AuditPath;
import com.codeaspect.sheldon.entity.GroupConfiguration;
import com.codeaspect.sheldon.exceptions.ConversionException;
import com.codeaspect.sheldon.helpers.AuditListHelper;
import com.codeaspect.sheldon.helpers.ComparatorHelper;
import com.codeaspect.sheldon.helpers.FieldMetadataHelper;
import com.codeaspect.sheldon.helpers.ObjectReflection;

public class AuditChecker<T> {

	
	@SuppressWarnings("unchecked")
	public List<AuditChangeEntry> checkObjects(T obj1, T obj2, AuditPath path) {
		if (obj1 == null && obj2 == null) {
			return new ArrayList<AuditChangeEntry>(0);
		} else if (obj1 == null ^ obj2 == null) {
			return Arrays.asList(AuditChangeEntry.modifyEntry(path, obj1, obj2));
		}

		if (obj1.getClass().isAnnotationPresent(Auditable.class)) {
			throw new ConversionException("Class must be annotated with @Auditable");
		}

		List<AuditChangeEntry> auditChangeEntry = new ArrayList<AuditChangeEntry>();
		ObjectReflection reflectionObject1 = new ObjectReflection(obj1), reflectionObject2 = new ObjectReflection(obj2);

		for (Field fld : FieldMetadataHelper.getAuditableFields(obj1.getClass())) {
			AuditField auditField = fld.getAnnotation(AuditField.class);
			
			@SuppressWarnings("rawtypes")
			Comparator comparator = ComparatorHelper.getFieldComparator(fld);
			
			Object value1 = reflectionObject1.getFieldValue(fld), value2 = reflectionObject2.getFieldValue(fld);

			AuditPath auditPath = new AuditPath(path, fld.getName(), auditField.fieldName(), auditField.groups());
			if (fld.getType().getAnnotation(Auditable.class) != null) {
				List<AuditChangeEntry> innerEntityChanges = (new AuditChecker<Object>().checkObjects(value1, value2,
						auditPath));
				auditChangeEntry.addAll(innerEntityChanges);
			} else if (comparator.compare(value1, value2) != 0) {
				auditChangeEntry.add(AuditChangeEntry.modifyEntry(auditPath, value1, value2));
			}
		}

		for (Field fld : FieldMetadataHelper.getAuditableListFields(obj1.getClass())) {
			AuditListHelper helper = new AuditListHelper(fld);
			List<AuditChangeEntry> listChanges = helper.checkList(obj1, obj2, path);
			auditChangeEntry.addAll(listChanges);
		}

		return auditChangeEntry;
	}

	public List<AuditChangeEntry> checkObjects(T obj1, T obj2) {
		if (obj1 == null && obj2 == null) {
			return new ArrayList<AuditChangeEntry>(0);
		} else if (obj1 == null ^ obj2 == null) {
			return Arrays.asList(AuditChangeEntry.deleteEntry(null, obj2));
		}

		if (obj1.getClass().isAnnotationPresent(Auditable.class)) {
			throw new ConversionException("Class must be annotated with @Auditable");
		}
		
		return checkObjects(obj1, obj2, AuditPath.EMPTY);
	}
	
	public List<AuditChangeEntry> checkObjects(T obj1, T obj2, AuditPath path, GroupConfiguration gc){
		List<AuditChangeEntry> results = checkObjects(obj1, obj2, path);
		return gc.filter(results);
	}
	
	public List<AuditChangeEntry> checkObjects(T obj1, T obj2, GroupConfiguration gc){
		List<AuditChangeEntry> results = checkObjects(obj1, obj2, AuditPath.EMPTY, gc);
		return gc.filter(results);
	}
	
	public List<AuditChangeEntry> checkObjects(T obj1, T obj2, String... groups){
		return checkObjects(obj1, obj2, AuditPath.EMPTY, new GroupConfiguration(true, groups));
	}
}