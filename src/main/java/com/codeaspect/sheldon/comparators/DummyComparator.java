package com.codeaspect.sheldon.comparators;

import java.util.Comparator;

public class DummyComparator implements Comparator<Object> {

	public int compare(Object o1, Object o2) {
		assert(false) : "This method should never be called. DummyComparator stands in place of null";
		return 0;
	}

}
