package com.codeaspect.sheldon.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.codeaspect.sheldon.helpers.StringUtil;

public class AuditPath {
	
	public static AuditPath EMPTY = null;

	private AuditPath parent;

	private String path;

	private String description;

	private String[] groups;

	public AuditPath(AuditPath parent, String path, String description, String[] groups) {
		this.parent = parent;
		this.path = path;
		this.description = description;
		this.groups = groups;
	}

	public AuditPath getParent() {
		return parent;
	}

	public List<String> getPath() {
		List<String> fullPath = new ArrayList<String>();
		if (parent != EMPTY) {
			fullPath.addAll(parent.getPath());
		}
		fullPath.add(path);
		return fullPath;
	}

	public String getPathString(String seperator) {
		List<String> pathList = getPath();
		return StringUtil.listToDelimitedString(pathList, seperator);
	}

	public String getPathString() {
		return getPathString(".");
	}

	public List<String> getDescription() {
		List<String> fullDescription = new ArrayList<String>();
		if (parent != EMPTY) {
			fullDescription.addAll(parent.getDescription());
		}
		fullDescription.add(description);
		return fullDescription;
	}

	public String getDescriptionString(String seperator) {
		List<String> descList = getDescription();
		return StringUtil.listToDelimitedString(descList, seperator);
	}

	public String getDescriptionString() {
		return getDescriptionString(">");
	}

	public String[] getGroups() {
		return groups;
	}

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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditPath other = (AuditPath) obj;
		return new EqualsBuilder().append(path, other.path).append(description, other.description).isEquals();
	}

}
