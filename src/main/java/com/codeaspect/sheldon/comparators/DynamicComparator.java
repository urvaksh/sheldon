package com.codeaspect.sheldon.comparators;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.codeaspect.sheldon.helpers.ObjectReflection;

/**
 * The Class DynamicComparator. This class compares two objects of the same type using introspection.
 * It iterates over every field the set of fields passed and compares the values.
 * Two objects are only deemed equal if all the field values are equal, in this case the class honors the contract of
 * Comparator and returns 0
 * . If two fields are not equal, the class checks if they are comparable, if comparable it returns the compared value
 * (-1 or 1), if not it moves to the next field (knowing that the return value will never be 0) to check if it is
 * comparable.
 * It can be stated that the call to compare will return -1 or 1 based on the first comparable field when it finds an
 * inequality.
 * If no field is comparable, i simply returns 1 to denote an inequality.
 */
public class DynamicComparator implements Comparator<Object> {

	/** The array of fields to be compared. */
	private Field[] fields;

	/**
	 * Instantiates a new dynamic comparator.
	 * 
	 * @param baseClass
	 *            the base class
	 * @param fieldList
	 *            the list of fields that need to be compared
	 */
	public DynamicComparator(Class<?> baseClass, String[] fieldList) {
		List<Field> fields = new ArrayList<Field>(fieldList.length);
		for (String fieldName : fieldList) {
			try {
				Field fld = getDeclaredField(baseClass, fieldName);
				fields.add(fld);
			} catch (Exception e) {
				throw new RuntimeException(String.format("No field %s in %s", fieldName, baseClass), e);
			}
		}
		this.fields = fields.toArray(new Field[] {});
	}

	/**
	 * Instantiates a new dynamic comparator.
	 * 
	 * @param flds
	 *            the set of fields to be compared
	 */
	public DynamicComparator(Set<Field> flds) {
		this.fields = flds.toArray(new Field[] {});
	}

	/**
	 * Gets the declared field.
	 * 
	 * @param delegateClazz
	 *            the delegate clazz
	 * @param fieldName
	 *            the field name
	 * @return the declared field
	 */
	private Field getDeclaredField(Class<?> delegateClazz, String fieldName) {

		Class<?> clazz = delegateClazz;

		while (clazz != null) {
			try {
				Field fld = clazz.getDeclaredField(fieldName);
				if (fld != null) {
					return fld;
				}
			} catch (NoSuchFieldException e) {
				if (clazz.getSuperclass() == null) {
					throw new RuntimeException(String.format("%s is not present in %s", fieldName,
							clazz.getCanonicalName()), e);
				}
				// Else do nothing, look for the field in the super class
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int compare(Object o1, Object o2) {
		ObjectReflection o1Reflect = new ObjectReflection(o1);
		ObjectReflection o2Reflect = new ObjectReflection(o2);
		boolean isEqual = true;
		int failedCompValue = 0;

		for (Field fld : fields) {
			Object val1 = o1Reflect.getFieldValue(fld);
			Object val2 = o2Reflect.getFieldValue(fld);

			if (val1 == null && val2 == null) {
				isEqual = true;
			} else if (val1 == null ^ val2 == null) {
				isEqual = false;
				failedCompValue = val1 == null ? -1 : 1;
			} else if (!val1.equals(val2)) {
				isEqual = false;
				if (failedCompValue == 0) {
					if (Comparable.class.isAssignableFrom(fld.getType())) {
						
						Comparable cmp = (Comparable) val1;
						failedCompValue = cmp.compareTo(val2);
						break;
					}
				}
			}

		}

		return isEqual ? 0 : (failedCompValue != 0 ? failedCompValue : 1);
	}

}
