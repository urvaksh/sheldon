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

	public String name();

	public Class<? extends Comparator<?>> comparator() default DummyComparator.class;

	public AuditComparator comparatorFields() default @AuditComparator;

}
