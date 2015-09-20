package com.codeaspect.sheldon.annonations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.codeaspect.sheldon.comparators.DynamicComparator;

/**
 * Defines the field names for a {@link DynamicComparator}
 * 
 * @author urvaksh.rogers
 *
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditComparator {
	/**
	 * List of field names that constitute the class's identity and need to be a
	 * part of the Dynamically created Comparator
	 * 
	 * @return
	 */
	public String[]value() default {};
}
