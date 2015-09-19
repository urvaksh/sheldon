package com.codeaspect.sheldon.comparators;

import java.util.Comparator;

/**
 * The Class EqualsComparator compares 2 objects based on their equals method.
 * If the methods are not equal, it simply returns 1 since it has no way of
 * knowing which value is larger
 * 
 * Note : NEVER use this Comparator on a List, it's only use is on component
 * fields and even in that case it's the default so there is no reason you would
 * ever have to explicitly declare it (hence no code samples as well).
 * 
 * @author urvaksh.rogers
 */
public class EqualsComparator implements Comparator<Object> {

	public int compare(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null ^ o2 == null) {
			return o1 == null ? -1 : 1;
		} else {
			return o1.equals(o2) ? 0 : 1;
		}
	}

}
