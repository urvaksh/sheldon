package com.codeaspect.sheldon.comparators;

import java.util.Comparator;

/**
 * If the classes being compared implement {@link Comparable}, and for the purpose of dirty checking, if the compareTo
 * method checks all the identity fields, then simply use this Comaprator.
 * 
 * @author urvaksh.rogers
 *
 * @param <T>
 */
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
