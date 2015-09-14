package com.codeaspect.sheldon.comparators;

import java.util.Comparator;

/**
 * The Class EqualsComparator compares 2 objects based on their equals method.<br />
 * If the methods are not equal, it simply returns 1 since it has no way of knowing which value is larger
 */
public class EqualsComparator implements Comparator<Object> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) {
		if(o1==null && o2==null){
			return 0;
		}else if(o1==null ^ o2==null){
			return o1==null?-1:1;
		}else{
			return o1.equals(o2)?0:1;
		}
	}

}
