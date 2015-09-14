package com.codeaspect.sheldon.entity;

public class AuditChangeEntry {

	private Action action;
	
	private AuditPath path;
	
	private Object value1, value2;

	public AuditChangeEntry(Action action, AuditPath path, Object value1,
			Object value2) {
		this.action = action;
		this.path = path;
		this.value1 = value1;
		this.value2 = value2;
	}

	public Action getAction() {
		return action;
	}

	public AuditPath getPath() {
		return path;
	}

	public Object getValue1() {
		return value1;
	}

	public Object getValue2() {
		return value2;
	}
	
	public static AuditChangeEntry createEntry(AuditPath path, Object value){
		return new AuditChangeEntry(Action.CREATE, path, value, null);
	}
	
	public static AuditChangeEntry deleteEntry(AuditPath path, Object value){
		return new AuditChangeEntry(Action.DELETE, path, null, value);
	}
	
	public static AuditChangeEntry modifyEntry(AuditPath path, Object value1, Object value2){
		return new AuditChangeEntry(Action.MODIFY, path, value1, value2);
	}

	@Override
	public String toString() {
		return "AuditChangeEntry [action=" + action + ", path=" + path
				+ ", value1=" + value1 + ", value2=" + value2 + "]";
	}
	
	
}
