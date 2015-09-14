package com.codeaspect.sheldon.annonations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;

import com.codeaspect.sheldon.comparators.DummyComparator;
import com.codeaspect.sheldon.converters.DefaultListConverter;
import com.codeaspect.sheldon.converters.ListConverter;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("rawtypes")
public @interface AuditableList {
	

	public Class<? extends Comparator> comparator() default DummyComparator.class;
	
	public AuditComparator comparatorFields() default @AuditComparator;
	
	public Class<? extends ListConverter> listConverter() default DefaultListConverter.class;
	
	public String[] groups() default {};
}
