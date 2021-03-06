package com.codeaspect.sheldon.entity;

/**
 * Represents the change on an
 * {@link com.codeaspect.sheldon.annonations.AuditField} and encompasses the
 * {@link Action}, path, present and previous values.
 *
 * @author urvaksh.rogers
 *
 */
public final class AuditChangeEntry {

	/**
	 * The {@link Action} that was performed on the object.
	 */
	private final Action action;

	/**
	 * The full {@link AuditPath} leading to the object.
	 */
	private final AuditPath path;

	/**
	 * The old and new values
	 */
	private final Object value1, value2;

	private AuditChangeEntry(final Action action, final AuditPath path, final Object value1, final Object value2) {
		this.action = action;
		this.path = path;
		this.value1 = value1;
		this.value2 = value2;
	}

	/**
	 * Get the {@link Action} performed.
	 *
	 * @return the {@link Action} describing the change
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Get the full {@link AuditPath} leading to the object.
	 *
	 * @return the full {@link AuditPath} leading to the object
	 */
	public AuditPath getPath() {
		return path;
	}

	/**
	 * Gets the original value (if present).
	 *
	 * @return the original value
	 */
	public Object getValue1() {
		return value1;
	}

	/**
	 * Gets the updated value (if present).
	 *
	 * @return the updated value
	 */
	public Object getValue2() {
		return value2;
	}

	/**
	 * Create a new Entry for when a Create Action is detected.
	 *
	 * @param path
	 *            the path to the newly created object
	 * @param value
	 *            the reference to the newly created object
	 * @return a {@link AuditChangeEntry} representing the create action
	 */
	public static AuditChangeEntry createEntry(final AuditPath path, final Object value) {
		return new AuditChangeEntry(Action.CREATE, path, value, null);
	}

	/**
	 * Create a new Entry for when a Delete Action is detected.
	 *
	 * @param path
	 *            the path of the deleted object
	 * @param value
	 *            the reference of the deleted object
	 * @return a {@link AuditChangeEntry} representing the delete action
	 */
	public static AuditChangeEntry deleteEntry(final AuditPath path, final Object value) {
		return new AuditChangeEntry(Action.DELETE, path, null, value);
	}

	/**
	 * Create a new Entry for when a Modify Action is detected.
	 *
	 * @param path
	 *            the path of the modified object
	 * @param value1
	 *            the original value
	 * @param value2
	 *            the updated value
	 * @return a {@link AuditChangeEntry} representing the modify action
	 */
	public static AuditChangeEntry modifyEntry(final AuditPath path, final Object value1, final Object value2) {
		return new AuditChangeEntry(Action.MODIFY, path, value1, value2);
	}

	@Override
	public String toString() {
		return "AuditChangeEntry [action=" + action + ", path=" + path + ", value1=" + value1 +
				", value2=" + value2 + "]";
	}

}
