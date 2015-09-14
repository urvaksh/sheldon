package com.codeaspect.sheldon.helpers.intg;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;

import com.codeaspect.sheldon.annonations.AuditComparator;
import com.codeaspect.sheldon.annonations.AuditField;
import com.codeaspect.sheldon.annonations.Auditable;
import com.codeaspect.sheldon.annonations.AuditableList;

@Ignore
public class Samples {

	@Ignore
	@Auditable(name = "Parent", comparatorFields = @AuditComparator({ "sample1", "sample2" }))
	static class Parent {
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

	}

	@Auditable(name = "sibling")
	static class Sibling {
		private Long someId;
		private String someOtherId;

		@AuditField(fieldName = "value", groups = "standard")
		private String value;

		public Sibling(Long someId, String someOtherId, String value) {
			this.someId = someId;
			this.someOtherId = someOtherId;
			this.value = value;
		}

	}

	@Ignore
	@Auditable(name = "Child")
	static class Child {

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

	}

}
