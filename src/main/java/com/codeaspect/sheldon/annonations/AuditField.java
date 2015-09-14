package com.codeaspect.sheldon.annonations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;

import com.codeaspect.sheldon.comparators.EqualsComparator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditField {

	public String fieldName();

	public Class<? extends Comparator<?>> comparator() default EqualsComparator.class;

	public AuditComparator comparatorFields() default @AuditComparator;

	public String[] groups() default {};
}
