package com.codeaspect.sheldon.helpers;

import java.util.List;
/**
 * Utility class to help with String manipulation.
 *
 * @author urvaksh.rogers
 *
 */
public class StringUtil {

	/**
	 * Utility class private constructor.
	 */
	private StringUtil() {
	}

	/**
	 * Converts and array to a String separating elements with the provided delimiters.
	 * @param arr a String array
	 * @param delim the delimiters to separate array elements
	 * @return the joined string
	 */
	public static String arrayToDelimitedString(final Object[] arr, final String delim) {
		if (arr == null || arr.length == 0) {
			return "";
		}
		if (arr.length == 1) {
			return arr[0] == null ? "" : arr[0].toString();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * Converts and array to a String separating elements with the provided delimiters.
	 * @param list a List of String
	 * @param delim the delimiters to separate array elements
	 * @return the joined string
	 */
	public static String listToDelimitedString(final List<String> list, final String delim) {
		return arrayToDelimitedString(list.toArray(), delim);
	}
}
