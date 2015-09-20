package com.codeaspect.sheldon.fieldcache;

import java.lang.reflect.Field;
import java.util.Set;

public interface FieldCache {

	boolean contains(Class<?> clazz);

	void put(Class<?> clazz, Set<Field> fields);

	Set<Field> get(Class<?> clazz);

}
