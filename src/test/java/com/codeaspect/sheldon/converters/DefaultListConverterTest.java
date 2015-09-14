package com.codeaspect.sheldon.converters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.codeaspect.sheldon.converters.DefaultListConverter;
import com.codeaspect.sheldon.converters.ListConverter;

public class DefaultListConverterTest {
	
	ListConverter<Iterable<?>> converter = new DefaultListConverter();
	
	@Test
	public void testConversiion(){
		final Set<Integer> integers = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6));
		
		@SuppressWarnings("unchecked")
		List<Integer> ints = (List<Integer>)converter.convertToList(null, new Iterable<Integer>(){
	          public Iterator<Integer> iterator() {
	              return integers.iterator();
	          }
	      });
		
		for(Integer i : ints){
			Assert.assertTrue(integers.contains(i));
		}
	}

}
