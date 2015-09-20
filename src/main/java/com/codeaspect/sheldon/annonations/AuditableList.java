package com.codeaspect.sheldon.annonations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;

import com.codeaspect.sheldon.comparators.DummyComparator;
import com.codeaspect.sheldon.converters.DefaultListConverter;
import com.codeaspect.sheldon.converters.ListConverter;

/**
 * Indicates that a Collection can be inspected for changes
 * 
 * @author urvaksh.rogers
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("rawtypes")
public @interface AuditableList {

	/**
	 * The comparator that is used to compare instances of the class this
	 * annotation is placed upon
	 * 
	 * @return
	 */
	public Class<? extends Comparator>comparator() default DummyComparator.class;

	/**
	 * Defines a the set of fields in the class that should be used to create a
	 * dynamic comparator to compare instances of the class this annotation is
	 * placed upon
	 * 
	 * @return
	 */
	public AuditComparator comparatorFields() default @AuditComparator ;

	public Class<? extends ListConverter>listConverter() default DefaultListConverter.class;

	public String[]groups() default {};
}
