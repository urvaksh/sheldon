package com.codeaspect.sheldon.intg.model;

import com.codeaspect.sheldon.annonations.AuditComparator;
import com.codeaspect.sheldon.annonations.AuditField;
import com.codeaspect.sheldon.annonations.Auditable;

@Auditable(name = "sibling", comparatorFields=@AuditComparator({"id","someOtherId"}))
public class Sibling {
	private Long someId;
	private String someOtherId;

	@AuditField(fieldName = "value", groups = "standard")
	private String value;

	public Sibling(Long someId, String someOtherId, String value) {
		this.someId = someId;
		this.someOtherId = someOtherId;
		this.value = value;
	}

	public Long getSomeId() {
		return someId;
	}

	public String getSomeOtherId() {
		return someOtherId;
	}

	public String getValue() {
		return value;
	}
	
}