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

	@Override
	public boolean contains(final Class<?> clazz) {
		return fieldCache.containsKey(clazz);
	}

	@Override
	public void put(final Class<?> clazz, final Set<Field> fields) {
		fieldCache.put(clazz, fields);
	}

	@Override
	public Set<Field> get(final Class<?> clazz) {
		return fieldCache.get(clazz);
	}

}
