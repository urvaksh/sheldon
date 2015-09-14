package com.codeaspect.sheldon.comparators;

import static junit.framework.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.codeaspect.sheldon.annonations.AuditField;
import com.codeaspect.sheldon.annonations.Auditable;
import com.codeaspect.sheldon.comparators.DynamicComparator;

public class DynamicComparatorTest {
	
	@Ignore
	@Auditable(name="Test Class")
	class TestClass{
		
		@AuditField(fieldName="First String")
		private String str;
		
		@AuditField(fieldName="First Integer")
		private Integer integer;

		public TestClass(String str, Integer integer) {
			this.str = str;
			this.integer = integer;
		}
	}
	
	private static String[] fieldNames = new String[]{"str","integer"};
	
	DynamicComparator comparator = new DynamicComparator(TestClass.class, fieldNames);
	
	@Test
	public void testEquality(){
		int comparison=comparator.compare(new TestClass("abc", 1), new TestClass("abc", 1));
		assertEquals(0, comparison);
	}
	
	@Test
	public void testInequalityFirst(){
		int comparison=comparator.compare(new TestClass("Abc", 1), new TestClass("abc", 1));
		assertTrue(comparison<0);
		
		int comparison2=comparator.compare(new TestClass("abc", 1), new TestClass("Abc", 1));
		assertTrue(comparison2>0);
	}
	
	@Test
	public void testInequalitySecond(){
		int comparison=comparator.compare(new TestClass("Abc", 1), new TestClass("Abc", 2));
		assertTrue(comparison<0);
		
		int comparison2=comparator.compare(new TestClass("Abc", 2), new TestClass("Abc", 1));
		assertTrue(comparison2>0);
	}

}
