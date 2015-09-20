package com.codeaspect.sheldon.converters;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
/**
 * Converts any Iterable to a List
 *
 * @author urvaksh.rogers
 *
 */
public class DefaultListConverter implements ListConverter<Iterable<?>> {

	@Override
	public List<?> convertToList(final Field fld, final Iterable<?> iterable) {
		return IteratorUtils.toList(iterable.iterator());
	}

}
