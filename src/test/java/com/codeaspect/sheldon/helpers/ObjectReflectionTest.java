package com.codeaspect.sheldon.helpers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Ignore;
import org.junit.Test;

import com.codeaspect.sheldon.helpers.ObjectReflection;

public class ObjectReflectionTest {
	
	@Ignore
	public static class TestClass{
		
		private String _str;
		public String String;
		private String string;
		
		public TestClass(){}
		
		public TestClass(String _str, String string, String string2) {
			this._str = _str;
			this.String = string;
			this.string = string2;
		}
		
		public String getString() {
			return string;
		}
		public void setString(String string) {
			this.string = string;
		}
		
	}
	
	@Test
	public void testGetFieldByName(){
		ObjectReflection reflection = new ObjectReflection(new TestClass());
		
		Field fld1 = reflection.getFieldByName("_str");
		assertEquals("_str", fld1.getName());
		assertEquals("com.codeaspect.sheldon.helpers.ObjectReflectionTest.TestClass", fld1.getDeclaringClass().getCanonicalName());
		
		Field fld2 = reflection.getFieldByName("String");
		assertEquals("String", fld2.getName());
		assertEquals("com.codeaspect.sheldon.helpers.ObjectReflectionTest.TestClass", fld2.getDeclaringClass().getCanonicalName());
		
		Field fld3 = reflection.getFieldByName("string");
		assertEquals("string", fld3.getName());
		assertEquals("com.codeaspect.sheldon.helpers.ObjectReflectionTest.TestClass", fld3.getDeclaringClass().getCanonicalName());
	}
	
	@Test
	public void testGetFieldValue(){
		ObjectReflection reflection = new ObjectReflection(new TestClass("1","2","3"));
		assertEquals("1", reflection.getFieldValue(reflection.getFieldByName("_str")));
		assertEquals("2", reflection.getFieldValue(reflection.getFieldByName("String")));
		assertEquals("3", reflection.getFieldValue(reflection.getFieldByName("string")));
	}
	
	@Test
	public void testSetFieldValue(){
		TestClass obj = new TestClass();
		ObjectReflection reflection = new ObjectReflection(obj);
		
		reflection.setFieldValue(reflection.getFieldByName("_str"),"A");
		assertEquals("A", obj._str);
		
		reflection.setFieldValue(reflection.getFieldByName("String"),"B");
		assertEquals("B", obj.String);
		
		reflection.setFieldValue(reflection.getFieldByName("String"),"C");
		assertEquals("C", obj.string);
		
	}

}
