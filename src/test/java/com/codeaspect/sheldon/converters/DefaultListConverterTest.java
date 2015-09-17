package com.codeaspect.sheldon.converters;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class DefaultListConverterTest {
	
	ListConverter<Iterable<?>> converter = new DefaultListConverter();
	
	@Test
	public void testConversiion(){
		final Set<Integer> integers = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6));
		
		@SuppressWarnings("unchecked")
		List<Integer> ints = (List<Integer>) converter.convertToList(null, integers);
		
		for(Integer i : ints){
			assertTrue(integers.contains(i));
		}
	}

}
