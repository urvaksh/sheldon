package com.codeaspect.sheldon.comparators;

import static junit.framework.Assert.*;

import org.junit.Test;

import com.codeaspect.sheldon.comparators.ComparableComparator;

public class ComparableComparatorTest {

	ComparableComparator<String> comparator = new ComparableComparator<String>();
	
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
	public void testLesser() {
		int comparison = comparator.compare("Test2", "test2");
		assertTrue("Comparison failed", comparison < 0);
	}
	
	@Test
	public void testLesserWithNull() {
		int comparison = comparator.compare(null, "test2");
		assertTrue("Comparison failed", comparison < 0);
	}

	@Test
	public void testGreater() {
		int comparison = comparator.compare("test3", "Test3");
		assertTrue("Comparison failed", comparison > 0);
	}
	
	@Test
	public void testGreaterWithNull() {
		int comparison = comparator.compare("test3", null);
		assertTrue("Comparison failed", comparison > 0);
	}

}
