package com.codeaspect.sheldon.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

public class ObjectReflection {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(ObjectReflection.class);

	private final Object delegate;

	public ObjectReflection(final Object delegate) {
		this.delegate = delegate;
	}

	private String makeErrorMessage(final String errorMsg, final Field field, final String... errors) {
		StringBuilder builder = new StringBuilder(errorMsg).append(field.getType().getCanonicalName()).append(".")
				.append(field.getName());

		for (String error : errors) {
			builder.append(error);
		}

		return builder.toString();
	}

	public Object getFieldValue(final Field fld) {
		if (fld.isAccessible()) {
			try {
				return fld.get(delegate);
			} catch (Exception e) {
				throw new RuntimeException(makeErrorMessage("Unable to set value of", fld), e);
			}
		} else {
			String getterMethodName = "get" + StringUtils.capitalize(fld.getName());
			try {
				Method getterMethod = fld.getDeclaringClass().getMethod(getterMethodName);
				if (!getterMethod.isAccessible()) {
					getterMethod.setAccessible(true);
				}
				return getterMethod.invoke(delegate);
			} catch (NoSuchMethodException e) {
				// Do nothing, skip to attempt direct field access
				LOG.warn("No getter for non accessable field. Attempting direct field access");
			} catch (Exception e) {
				throw new RuntimeException(makeErrorMessage("Unable to invoke getter on field", fld), e);
			}

			// Direct Field access
			try {
				fld.setAccessible(true);
				return fld.get(delegate);
			} catch (Exception e) {
				throw new RuntimeException(makeErrorMessage("Unable to directly get value from ", fld), e);
			}
		}
	}

	public Field getFieldByName(final String name) {
		Class<?> clazz = delegate.getClass();

		while (clazz != null) {
			try {
				Field fld = clazz.getDeclaredField(name);
				if (fld != null) {
					return fld;
				}
			} catch (NoSuchFieldException e) {
				if (clazz.getSuperclass() == null) {
					throw new RuntimeException(String.format("%s is not present in %s", name, clazz.getCanonicalName()),
							e);
				}
				// Else do nothing, look for the field in the super class
			}
			clazz = clazz.getSuperclass();
		}

		return null;
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ObjectReflection other = (ObjectReflection) obj;
		if (delegate == null) {
			if (other.delegate != null) {
				return false;
			}
		} else if (!delegate.equals(other.delegate)) {
			return false;
		}
		return true;
	}
}
