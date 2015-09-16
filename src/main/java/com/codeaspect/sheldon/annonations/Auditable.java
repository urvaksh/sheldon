package com.codeaspect.sheldon.annonations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;

import com.codeaspect.sheldon.comparators.DummyComparator;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {

	/**
	 * The name of the Object as you want it to appear in the Audit Path
	 * 
	 * @return
	 */
	public String name();

	/**
	 * The comparator that is used to compare instances of the class this annotation is placed upon
	 * 
	 * @return
	 */
	public Class<? extends Comparator<?>> comparator() default DummyComparator.class;

	/**
	 * Defines a the set of fields in the class that should be used to create a dynamic comparator to compare instances
	 * of the class this annotation is placed upon
	 * 
	 * @return
	 */
	public AuditComparator comparatorFields() default @AuditComparator;

}
