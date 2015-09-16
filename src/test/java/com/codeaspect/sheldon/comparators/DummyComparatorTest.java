package com.codeaspect.sheldon.comparators;

import org.junit.Test;

public class DummyComparatorTest {
	
	@Test(expected=AssertionError.class)
	public void testDummyComparator(){
		new DummyComparator().compare(1L, 2L);
	}

}
