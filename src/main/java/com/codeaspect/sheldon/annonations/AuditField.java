package com.codeaspect.sheldon.annonations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;

import com.codeaspect.sheldon.comparators.EqualsComparator;
/**
 * Defines that the annotated field should be dirty checked
 * 
 * @author urvaksh.rogers
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditField {

	/**
	 * The name of the field as you want it to appear in the Audit Path
	 * 
	 * @return
	 */
	public String fieldName();

	/**
	 * The comparator that is used to compare instances of the class this annotation is placed upon
	 * 
	 * @return
	 */
	public Class<? extends Comparator<?>> comparator() default EqualsComparator.class;

	/**
	 * Defines a the set of fields in the class that should be used to create a dynamic comparator to compare instances
	 * of the class this annotation is placed upon
	 * 
	 * @return
	 */
	public AuditComparator comparatorFields() default @AuditComparator;

	/**
	 * Defines the groups for this field. The framework can allow dirty checking for certain groups, if the group is in
	 * this list, it will be reported, otherwise it will be skipped.
	 * 
	 * @return
	 */
	public String[] groups() default {};
}
