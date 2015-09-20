package com.codeaspect.sheldon.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.codeaspect.sheldon.annonations.Auditable;
import com.codeaspect.sheldon.annonations.AuditableList;
import com.codeaspect.sheldon.helpers.StringUtil;

/**
 * A representation of the full path leading to an object The path holds the
 * property names as well as descriptions provided in the {@link Auditable} and
 * {@link AuditableList} annotations.
 *
 * @author urvaksh.rogers
 *
 */
public class AuditPath {

	public static final AuditPath EMPTY = null;

	private final AuditPath parent;

	private final String path;

	private final String description;

	private final String[] groups;

	/**
	 * Creates a new {@link AuditPath}
	 *
	 * @param parent
	 *            makes this path as it's parent
	 * @param path
	 *            the object path (property name)
	 * @param description
	 *            the description of the property as set in the
	 *            {@link Auditable} and {@link AuditableList} annotations
	 * @param groups
	 *            the list of groups this path belongs to
	 */
	public AuditPath(AuditPath parent, String path, String description, String[] groups) {
		this.parent = parent;
		this.path = path;
		this.description = description;
		this.groups = groups.clone();
	}

	/**
	 * Gets the parent path
	 *
	 * @return
	 */
	public AuditPath getParent() {
		return parent;
	}

	/**
	 * Gets a String List representing the path (property names).
	 *
	 * @return List of String representing the path (property names).
	 */
	public List<String> getPath() {
		List<String> fullPath = new ArrayList<String>();
		if (parent != EMPTY) {
			fullPath.addAll(parent.getPath());
		}
		fullPath.add(path);
		return fullPath;
	}

	/**
	 * Creates a String representing the full path
	 *
	 * @param separator
	 *            the separator used to tokenize each part of the path
	 * @return A string representation of the path
	 */
	public String getPathString(String separator) {
		List<String> pathList = getPath();
		return StringUtil.listToDelimitedString(pathList, separator);
	}

	/**
	 * Creates a String representing the full path with the "." character as the
	 * separator
	 *
	 * @return A string representation of the path
	 */
	public String getPathString() {
		return getPathString(".");
	}

	/**
	 * Gets a String List representing the descriptions of the path leading to
	 * the object.
	 *
	 * @return List of String representing the descriptions of the path leading
	 *         to the object.
	 */
	public List<String> getDescription() {
		List<String> fullDescription = new ArrayList<String>();
		if (parent != EMPTY) {
			fullDescription.addAll(parent.getDescription());
		}
		fullDescription.add(description);
		return fullDescription;
	}

	/**
	 * Creates a String representing the the descriptions of the full path
	 * leading to the object.
	 *
	 * @param separator
	 *            the separator used to tokenize each part of the path
	 * @return A string representation of the descriptions
	 */
	public String getDescriptionString(String separator) {
		List<String> descList = getDescription();
		return StringUtil.listToDelimitedString(descList, separator);
	}

	/**
	 * Creates a String representing the the descriptions of the full path
	 * leading to the object with the "&gt;" character as the separator.
	 *
	 * @return A string representation of the descriptions
	 */
	public String getDescriptionString() {
		return getDescriptionString(">");
	}

	/**
	 * Gets the groups associated with the change
	 *
	 * @return array of groups
	 */
	public String[] getGroups() {
		return groups.clone();
	}

	/**
	 * Gets the groups associated with the change and all the parent groups
	 * leading to the changed object
	 *
	 * @return array of inherited groups
	 */
	public String[] getInheritedGroups() {
		Set<String> groups = new HashSet<String>();
		groups.addAll(Arrays.asList(this.groups));
		if (parent != EMPTY) {
			groups.addAll(Arrays.asList(parent.getInheritedGroups()));
		}
		return groups.toArray(new String[] {});
	}

	@Override
	public String toString() {
		return new StringBuilder().append("{[Path: ").append(path).append("] Description[").append(description)
				.append("]}").toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(path).append(description).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AuditPath other = (AuditPath) obj;
		return new EqualsBuilder().append(path, other.path).append(description, other.description).isEquals();
	}

}
