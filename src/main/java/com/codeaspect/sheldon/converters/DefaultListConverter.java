package com.codeaspect.sheldon.converters;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;

public class DefaultListConverter implements ListConverter<Iterable<?>> {

	public List<?> convertToList(Field fld, Iterable<?> iterable) {
		return IteratorUtils.toList(iterable.iterator());
	}

}
