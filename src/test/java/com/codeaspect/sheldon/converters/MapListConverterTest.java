package com.codeaspect.sheldon.converters;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class MapListConverterTest {

	@Test
	public void testMapConversion() {

		Map<String, String> m = Arrays.asList("D", "A", "Z").parallelStream()
				.collect(Collectors.toMap(s -> s.toString().toUpperCase(), s -> s.toString().toLowerCase()));

		String op = new MapListConverter()
			.convertToList(null, m)
			.stream()
			.reduce("", (a, s) -> a + s);

		Assert.assertEquals("adz", op);

	}
}

class MapListConverter implements ListConverter<Map<String, String>> {

	public List<String> convertToList(Field fld, final Map<String, String> map) {
	
		return map.keySet()
			.stream()
			.sorted()
			.map(key-> map.get(key))
			.collect(Collectors.toList());
		
	}

}
