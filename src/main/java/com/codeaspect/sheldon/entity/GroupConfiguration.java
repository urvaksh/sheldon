package com.codeaspect.sheldon.entity;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

/**
 * Represents a group and determines the inheritance rules for all the groups.
 * @author urvaksh.rogers
 *
 */
public class GroupConfiguration {

	private String[] groups;

	private boolean allowInheritGroups;

	/*
	 * Creates a GroupConfiguration
	 */
	public GroupConfiguration(boolean allowInheritGroups, String... groups) {
		this.groups = groups;
		this.allowInheritGroups = allowInheritGroups;
	}

	/**
	 * Gets the groups in GroupConfiguration
	 * @return
	 */
	public String[] getGroups() {
		return groups;
	}

	/**
	 * Gets the allowInheritGroups property
	 * @return allowInheritGroups property
	 */
	public boolean isAllowInheritGroups() {
		return allowInheritGroups;
	}

	/**
	 * Gets all the groups for a given path
	 * @param path the path leading to the change
	 * @return array of group names
	 */
	public String[] getGroups(AuditPath path) {
		return allowInheritGroups ? path.getInheritedGroups() : path.getGroups();
	}

	/**
	 * Filters the results based on the groups and inheritance configuration
	 * @param completeResults
	 * @return filtered List of {@link AuditChangeEntry}
	 */
	public List<AuditChangeEntry> filter(List<AuditChangeEntry> completeResults) {
		return (List<AuditChangeEntry>) CollectionUtils.select(completeResults, new Predicate<AuditChangeEntry>() {
			public boolean evaluate(AuditChangeEntry entry) {
				for (String groupName : GroupConfiguration.this.getGroups()) {
					List<String> allGroups = Arrays.asList(GroupConfiguration.this.getGroups(entry.getPath()));
					if (allGroups.contains(groupName)) {
						return true;
					}
				}
				return false;
			}
		});
	}

}
