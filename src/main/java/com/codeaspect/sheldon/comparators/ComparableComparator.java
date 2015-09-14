package com.codeaspect.sheldon.comparators;

import java.util.Comparator;

public class ComparableComparator<T extends Comparable<T>> implements Comparator<T> {

	public int compare(T o1, T o2) {
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null ^ o2 == null) {
			return o1 == null ? -1 : 1;
		} else {
			return o1.compareTo(o2);
		}
	}

}
