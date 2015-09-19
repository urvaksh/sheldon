package com.codeaspect.sheldon.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Comparator;

import com.codeaspect.sheldon.annonations.AuditField;
import com.codeaspect.sheldon.annonations.Auditable;
import com.codeaspect.sheldon.annonations.AuditableList;
import com.codeaspect.sheldon.comparators.DynamicComparator;
import com.codeaspect.sheldon.exceptions.SheldonException;

public class ComparatorHelper {

	@SuppressWarnings("rawtypes")
	public static Comparator getFieldComparator(Field fld) {
		AuditField fldAnnotation = fld.getAnnotation(AuditField.class);
		Class<?> fieldType = fld.getType();

		try {
			if (fldAnnotation.comparatorFields().value().length > 0) {
				return new DynamicComparator(fieldType, fldAnnotation.comparatorFields().value());
			} else {
				return fldAnnotation.comparator().newInstance();
			}
		} catch (Exception e) {
			throw new SheldonException("Unable to create comparator", e);
		}
	}

	private static boolean compareComparatorClasses(Class<? extends Annotation> clazz, String annotationField,
			Class<?> actualClazz) throws NoSuchMethodException, SecurityException {
		return clazz.getMethod(annotationField).getDefaultValue().equals(actualClazz);
	}

	@SuppressWarnings("rawtypes")
	public static Comparator getListComparator(Field fld) {
		AuditableList listAnnotation = fld.getAnnotation(AuditableList.class);
		Class<?> genericType = FieldMetadataHelper.getGenericType(fld);
		Auditable fldAnnotation = genericType.getAnnotation(Auditable.class);

		try {
			if (listAnnotation.comparatorFields().value().length > 0) {
				return new DynamicComparator(genericType, listAnnotation.comparatorFields().value());
			} else if (!compareComparatorClasses(AuditableList.class, "comparator", listAnnotation.comparator())) {
				return listAnnotation.comparator().newInstance();
			} else if (fldAnnotation.comparatorFields().value().length > 0) {
				return new DynamicComparator(genericType, fldAnnotation.comparatorFields().value());
			} else if (!compareComparatorClasses(Auditable.class, "comparator",
					genericType.getAnnotation(Auditable.class).comparator())) {
				return genericType.getAnnotation(Auditable.class).comparator().newInstance();
			} else {
				return new DynamicComparator(FieldMetadataHelper.getAuditableFields(genericType));
			}
		} catch (Exception e) {
			throw new SheldonException("Failed to create Comparator", e);
		}

	}

}
