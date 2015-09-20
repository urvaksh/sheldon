package com.codeaspect.sheldon.comparators;

import java.io.Serializable;
import java.util.Comparator;

/**
 * If the classes being compared implement {@link Comparable}, and for the
 * purpose of dirty checking, if the compareTo method checks all the identity
 * fields, then simply use this Comaprator.
 *
 * @author urvaksh.rogers
 *
 * @param <T>
 *            the type of objects that may be compared by this comparator
 */
public class ComparableComparator<T extends Comparable<T>> implements Comparator<T>, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4225442331637558394L;

	@Override
	public int compare(final T o1, final T o2) {
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null ^ o2 == null) {
			return o1 == null ? -1 : 1;
		} else {
			return o1.compareTo(o2);
		}
	}

}
