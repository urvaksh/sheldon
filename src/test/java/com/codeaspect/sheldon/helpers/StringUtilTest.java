package com.codeaspect.sheldon.helpers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.codeaspect.sheldon.helpers.StringUtil;

public class StringUtilTest {
	
	@Test
	public void testArrayToDelimitedString_Empty(){
		String[] strs = new String[]{};
		assertEquals("",StringUtil.arrayToDelimitedString(strs, ","));
	}

	@Test
	public void testArrayToDelimitedString_Null(){
		assertEquals("",StringUtil.arrayToDelimitedString(null, ","));
	}
	
	@Test
	public void testArrayToDelimitedString_SingleEntry(){
		final String str = "ABC";
		String[] strs = new String[]{str};
		assertEquals(str,StringUtil.arrayToDelimitedString(strs, ","));
	}
	
	@Test
	public void testArrayToDelimitedString(){
		String[] strs = new String[]{"ABC","XYZ"};
		assertEquals("ABC,XYZ",StringUtil.arrayToDelimitedString(strs, ","));
	}
	
	@Test
	public void testListToDelimitedString(){
		String[] strs = new String[]{"ABC","XYZ"};
		List<String> list = Arrays.asList(strs);
		assertEquals("ABC,XYZ",StringUtil.listToDelimitedString(list, ","));
	}
}
