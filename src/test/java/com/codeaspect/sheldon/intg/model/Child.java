package com.codeaspect.sheldon.intg.model;

import org.junit.Ignore;

import com.codeaspect.sheldon.annonations.AuditField;
import com.codeaspect.sheldon.annonations.Auditable;

@Ignore
@Auditable(name = "Child")
public class Child {

	private Long id;

	@AuditField(fieldName = "fld1", groups = { "standrd" })
	private String fld1;

	@AuditField(fieldName = "fld2", groups = { "standrd", "wacky" })
	private String fld2;

	public Child(Long id, String fld1, String fld2) {
		this.id = id;
		this.fld1 = fld1;
		this.fld2 = fld2;
	}

	public Long getId() {
		return id;
	}

	public String getFld1() {
		return fld1;
	}

	public String getFld2() {
		return fld2;
	}

}