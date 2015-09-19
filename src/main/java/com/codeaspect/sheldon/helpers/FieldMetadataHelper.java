package com.codeaspect.sheldon.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import com.codeaspect.sheldon.annonations.AuditField;
import com.codeaspect.sheldon.annonations.AuditableList;
import com.codeaspect.sheldon.exceptions.SheldonException;
import com.codeaspect.sheldon.fieldcache.FieldCache;

/**
 * The Class FieldMetadataHelper helps fetch metadata from a class.
 */
public class FieldMetadataHelper {

	/**
	 * Gets the fields annotated with @Auditable.
	 * 
	 * @param clazz
	 *            the class to introspect for @Auditable fields
	 * @return the auditable fields
	 */
	public static Set<Field> getAuditableFields(Class<?> clazz) {
		return getFieldsFromStaticCache(clazz, AuditField.class, auditFieldCache);
	}

	/**
	 * Gets the fields annotated with @AuditableList.
	 * 
	 * @param clazz
	 *            the class to introspect for @AuditableList fields
	 * @return the auditable list fields
	 */
	public static Set<Field> getAuditableListFields(Class<?> clazz) {
		return getFieldsFromStaticCache(clazz, AuditableList.class,
				auditListCache);
	}

	/**
	 * Gets the declared field.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param name
	 *            the name
	 * @return the declared field
	 */
	public Field getDeclaredField(Class<?> clazz, String name) {
		Class<?> baseClazz = clazz;

		while (baseClazz != null) {
			Field fld;
			try {
				fld = baseClazz.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				fld = null;
			}

			if (fld != null) {
				return fld;
			}
			
			baseClazz=baseClazz.getSuperclass();
		}
		throw new SheldonException(String.format(
				"Class %s does not have field named %s",
				clazz==null?"Object":clazz.getCanonicalName(), name));
	}

	public static Class<?> getGenericType(Field fld) {
		if (fld.getGenericType() instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) fld.getGenericType();

			if (pType.getActualTypeArguments().length > 0) {
				return (Class<?>) pType.getActualTypeArguments()[0];
			}

		}
		return Object.class;
	}

	/**
	 * Creates the fields from annotation.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param annotation
	 *            the annotation
	 * @return the sets the
	 */
	private static Set<Field> createFieldsFromAnnotation(final Class<?> clazz,
			final Class<? extends Annotation> annotation) {
		List<Field> fieldList = new ArrayList<Field>();

		Class<?> currentClass = clazz;
		while (currentClass != null) {
			Collections.addAll(fieldList, currentClass.getDeclaredFields());
			currentClass = currentClass.getSuperclass();
		}

		
		CollectionUtils.filter(fieldList, new Predicate<Field>() {
			public boolean evaluate(Field fld) {
				return fld.getAnnotation(annotation) == null ? false : true;
			}
		});

		return new HashSet<Field>(fieldList);
	}

	/**
	 * Gets the fields from static cache.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param annotation
	 *            the annotation
	 * @param cache
	 *            the cache
	 * @return the fields from static cache
	 */
	private static Set<Field> getFieldsFromStaticCache(Class<?> clazz,
			Class<? extends Annotation> annotation, FieldCache cache) {
		if (cache != null && cache.contains(clazz)) {
			return cache.get(clazz);
		} else {
			Set<Field> fields = createFieldsFromAnnotation(clazz, annotation);
			if (cache != null) {
				synchronized (clazz) {
					cache.put(clazz, fields);
				}
			}
			return fields;
		}
	}

	private static FieldCache auditListCache = null;

	private static FieldCache auditFieldCache = null;

	public static void setAuditFieldCache(FieldCache auditFieldCache) {
		FieldMetadataHelper.auditFieldCache = auditFieldCache;
	}

	public static void setAuditListCache(FieldCache auditListCache) {
		FieldMetadataHelper.auditListCache = auditListCache;
	}

}
