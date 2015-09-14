package com.codeaspect.sheldon.helpers;

import java.util.List;

public class StringUtil {

	public static String arrayToDelimitedString(Object[] arr, String delim) {
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

	public static String listToDelimitedString(List<String> list, String delim) {
		return arrayToDelimitedString(list.toArray(), ",");
	}
}
