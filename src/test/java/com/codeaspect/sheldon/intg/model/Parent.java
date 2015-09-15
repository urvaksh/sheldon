package com.codeaspect.sheldon.intg.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;

import com.codeaspect.sheldon.annonations.AuditComparator;
import com.codeaspect.sheldon.annonations.AuditField;
import com.codeaspect.sheldon.annonations.Auditable;
import com.codeaspect.sheldon.annonations.AuditableList;

@Ignore
@Auditable(name = "Parent", comparatorFields = @AuditComparator({ "sample1", "sample2" }))
public class Parent {
	@AuditField(fieldName = "A Sample Field", groups = "standard")
	private String sample1;

	private Long sample2;

	@AuditField(fieldName = "sibling", groups = "standard")
	private Sibling sibling;

	@AuditableList(groups = "standard", comparatorFields = @AuditComparator("id"))
	private List<Child> children = new ArrayList<Child>();

	public Parent(String s1, Long s2) {
		this.sample1 = s1;
		this.sample2 = s2;
	}

	public Parent addChild(Child c) {
		this.children.add(c);
		return this;// Allows chaining
	}

	// Being a little mean here where you can have only 1 sibling. Really cool to ou if you are in Chna though!
	public Parent replaceSibling(Sibling sibling) {
		this.sibling = sibling;
		return this;// Allows chaining
	}

	public String getSample1() {
		return sample1;
	}

	public Long getSample2() {
		return sample2;
	}

	public Sibling getSibling() {
		return sibling;
	}

	public List<Child> getChildren() {
		return children;
	}
	
}