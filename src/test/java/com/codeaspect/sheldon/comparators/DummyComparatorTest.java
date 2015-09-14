package com.codeaspect.sheldon.comparators;

import org.junit.Test;

import com.codeaspect.sheldon.comparators.DummyComparator;

public class DummyComparatorTest {
	
	@Test
	public void testDummyComparator(){
		new DummyComparator().compare(1L, 2L);
	}

}
