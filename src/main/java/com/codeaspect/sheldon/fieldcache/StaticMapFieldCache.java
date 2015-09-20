package com.codeaspect.sheldon.fieldcache;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StaticMapFieldCache implements FieldCache {

	private static Map<Class<?>, Set<Field>> fieldCache = new HashMap<Class<?>, Set<Field>>();

	private StaticMapFieldCache() {
	}

	private static class LazyHolder {
		private static final StaticMapFieldCache INSTANCE = new StaticMapFieldCache();
	}

	public static StaticMapFieldCache getInstance() {
		return LazyHolder.INSTANCE;
	}

	public boolean contains(Class<?> clazz) {
		return fieldCache.containsKey(clazz);
	}

	public void put(Class<?> clazz, Set<Field> fields) {
		fieldCache.put(clazz, fields);
	}

	public Set<Field> get(Class<?> clazz) {
		return fieldCache.get(clazz);
	}

}
