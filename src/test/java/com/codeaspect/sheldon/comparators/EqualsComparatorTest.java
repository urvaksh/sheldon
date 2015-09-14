package com.codeaspect.sheldon.comparators;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.codeaspect.sheldon.comparators.EqualsComparator;

public class EqualsComparatorTest {
	
	EqualsComparator comparator = new EqualsComparator();
	
	@Test
	public void testEquality() {
		int comparison = comparator.compare("Test1", "Test1");
		assertEquals("Comparison failed", 0, comparison);
	}
	
	@Test
	public void testEqualityWithNull() {
		int comparison = comparator.compare(null, null);
		assertEquals("Comparison failed", 0, comparison);
	}
	
	@Test
	public void testInequality() {
		int comparison = comparator.compare("Test1", "test1");
		assertEquals("Comparison failed", 1, comparison);
	}
	
	@Test
	public void testInEqualityWithNull() {
		int comparison = comparator.compare("Test2", null);
		assertEquals("Comparison failed", 1, comparison);
		
		int comparison2 = comparator.compare(null, "Test2");
		assertEquals("Comparison failed", -1, comparison2);
	}
}
