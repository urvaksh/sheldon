package com.codeaspect.sheldon.comparators;

import java.util.Comparator;

import com.codeaspect.sheldon.annonations.Auditable;
/**
 * Placeholder for an actual {@link Comparator} in the {@link Auditable} annotation
 * 
 * @author urvaksh.rogers
 *
 */
public class DummyComparator implements Comparator<Object> {

	public int compare(Object o1, Object o2) {
		assert(false) : "This method should never be called. DummyComparator stands in place of null";
		return 0;
	}

}
