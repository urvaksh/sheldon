package com.codeaspect.sheldon.annonations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;

import com.codeaspect.sheldon.comparators.DummyComparator;

/**
 * Indicates that a class either at a top level or at a nested level can be
 * inspected for changes
 *
 * @author urvaksh.rogers
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {

	/**
	 * The name of the Object as you want it to appear in the Audit Path
	 *
	 * @return name of Object
	 */
	public String name();

	/**
	 * The {@link Comparator} that is used to compare instances of the class
	 * this annotation is placed upon
	 *
	 * @return a {@link Comparator}
	 */
	public Class<? extends Comparator<?>>comparator() default DummyComparator.class;

	/**
	 * Defines a the set of fields in the class that should be used to create a
	 * dynamic {@link Comparator} to compare instances of the class this
	 * annotation is placed upon
	 *
	 * @return the {@link AuditComparator} anotation containing the fields for a
	 *         dynamic {@link Comparator}
	 */
	public AuditComparator comparatorFields() default @AuditComparator ;

}
