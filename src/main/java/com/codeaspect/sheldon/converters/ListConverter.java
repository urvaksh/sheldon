package com.codeaspect.sheldon.converters;

import java.lang.reflect.Field;
import java.util.List;

public interface ListConverter<T extends Iterable<?>> {
	
	public List<?> convertToList(Field fld, T iterable);

}
