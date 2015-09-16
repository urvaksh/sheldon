package com.codeaspect.sheldon.entity;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

/**
 * Represents a group and determines the inheritance rules for all the groups.
 * @author urvaksh.rogers@gmail.com
 *
 */
public class GroupConfiguration {

	private String[] groups;

	private boolean allowInheritGroups;

	public GroupConfiguration(boolean allowInheritGroups, String... groups) {
		this.groups = groups;
		this.allowInheritGroups = allowInheritGroups;
	}

	public String[] getGroups() {
		return groups;
	}

	public boolean isAllowInheritGroups() {
		return allowInheritGroups;
	}

	public String[] getGroups(AuditPath path) {
		return allowInheritGroups ? path.getInheritedGroups() : path.getGroups();
	}

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
