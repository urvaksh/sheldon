package com.codeaspect.sheldon.intg;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.codeaspect.sheldon.AuditChecker;
import com.codeaspect.sheldon.entity.Action;
import com.codeaspect.sheldon.entity.AuditChangeEntry;

import static com.codeaspect.sheldon.intg.Structure.*;

public class FieldChangeTest {

	
	@Test
	public void simpleChangeTest(){
		Parent p1 = new Parent("A",2L);
		Parent p2 = new Parent("B",2L);
		AuditChecker<Parent> checker = new AuditChecker<Parent>();
		List<AuditChangeEntry> entries = checker.checkObjects(p1, p2);
		
		Assert.assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		Assert.assertEquals("A", entry.getValue1());
		Assert.assertEquals("B", entry.getValue2());
		Assert.assertEquals("sample1", entry.getPath().getPathString());
		Assert.assertEquals(Action.MODIFY, entry.getAction());
	}
	
	
	@Test
	public void simpleChangeUnAuditedFieldTest(){
		Parent p1 = new Parent("A",1L);
		Parent p2 = new Parent("A",2L);
		AuditChecker<Parent> checker = new AuditChecker<Parent>();
		List<AuditChangeEntry> entries = checker.checkObjects(p1, p2);
		
		Assert.assertEquals(0, entries.size());
	}
	
	@Test
	public void simpleChangeTestWithGroup(){
		Parent p1 = new Parent("A",2L);
		Parent p2 = new Parent("B",2L);
		AuditChecker<Parent> checker = new AuditChecker<Parent>();
		List<AuditChangeEntry> entries = checker.checkObjects(p1, p2,"standard");
		
		Assert.assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		Assert.assertEquals("A", entry.getValue1());
		Assert.assertEquals("B", entry.getValue2());
		Assert.assertEquals("sample1", entry.getPath().getPathString());
		Assert.assertEquals(Action.MODIFY, entry.getAction());
	}
	
	@Test
	public void simpleChangeTestWithDifferentGroup(){
		Parent p1 = new Parent("A",2L);
		Parent p2 = new Parent("B",2L);
		AuditChecker<Parent> checker = new AuditChecker<Parent>();
		List<AuditChangeEntry> entries = checker.checkObjects(p1, p2,"non-standard");
		
		Assert.assertEquals(0, entries.size());
	}
}
