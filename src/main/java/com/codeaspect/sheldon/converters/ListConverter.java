package com.codeaspect.sheldon.converters;

import java.lang.reflect.Field;
import java.util.List;
/**
 * Defines how a class is to be converted to a List
 *
 * @author urvaksh.rogers
 *
 * @param <T>
 */
public interface ListConverter<T> {

	public List<?> convertToList(Field fld, T iterable);

}
