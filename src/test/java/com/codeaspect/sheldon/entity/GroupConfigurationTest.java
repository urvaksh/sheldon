package com.codeaspect.sheldon.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GroupConfigurationTest {
	
	@Mock
	private AuditPath auditPath;
	
	@Test
	public void testBasicProperties(){
		GroupConfiguration gc = new GroupConfiguration(true,"A","B","C");
		assertEquals(true, gc.isAllowInheritGroups());
		Set<String> groups=new HashSet<String>(Arrays.asList(gc.getGroups()));
		assertTrue(groups.contains("A"));
		assertTrue(groups.contains("B"));
		assertTrue(groups.contains("C"));
	}
	
	public void testGetGroups_WithAllowInheritGroups(){
		Mockito.when(auditPath.getGroups()).thenReturn(new String[]{"A"});
		Mockito.when(auditPath.getInheritedGroups()).thenReturn(new String[]{"A","B"});
		
		GroupConfiguration gc = new GroupConfiguration(true,"A","B","C");
		
		Set<String> groups=new HashSet<String>(Arrays.asList(gc.getGroups()));
		assertTrue(groups.contains("B"));
	}
	
	public void testGetGroups_WithoutAllowInheritGroups(){
		Mockito.when(auditPath.getGroups()).thenReturn(new String[]{"A"});
		Mockito.when(auditPath.getInheritedGroups()).thenReturn(new String[]{"A","B"});
		
		GroupConfiguration gc = new GroupConfiguration(true,"A","B","C");
		
		Set<String> groups=new HashSet<String>(Arrays.asList(gc.getGroups()));
		assertFalse(groups.contains("B"));
	}

}
