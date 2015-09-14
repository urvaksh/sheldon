package com.codeaspect.sheldon.helpers.intg;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.codeaspect.sheldon.AuditChecker;
import com.codeaspect.sheldon.entity.Action;
import com.codeaspect.sheldon.entity.AuditChangeEntry;
import com.codeaspect.sheldon.helpers.intg.Samples.Parent;
import com.codeaspect.sheldon.helpers.intg.Samples.Sibling;

public class CompositeTest {

	private Parent getSameParent() {
		return new Parent("A", 1L);
	}
	
	@Test
	public void testCompositeChange(){
		Parent p1 = getSameParent().replaceSibling(new Sibling(1L, "A", "Chris"));
		Parent p2 = getSameParent().replaceSibling(new Sibling(1L, "A", "Jen"));
		
		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		Assert.assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		Assert.assertEquals("Chris", entry.getValue1());
		Assert.assertEquals("Jen", entry.getValue2());
		Assert.assertEquals("sibling>value", entry.getPath().getPathString(">"));
		Assert.assertEquals(Action.MODIFY, entry.getAction());
	}
	
	@Test
	public void testCompositeAdd(){ //Always shows up as modify, only lists can have add, but we still test it
		Sibling jen = new Sibling(1L, "A", "Jen");
		Parent p1 = getSameParent();
		Parent p2 = getSameParent().replaceSibling(jen);
		
		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		Assert.assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		Assert.assertEquals(null, entry.getValue1());
		Assert.assertEquals(jen, entry.getValue2());
		Assert.assertEquals("sibling", entry.getPath().getPathString(">"));
		Assert.assertEquals(Action.MODIFY, entry.getAction());
	}
	
	@Test
	public void testCompositeDelete(){ //Always shows up as modify, only lists can have add, but we still test it
		Sibling chris = new Sibling(1L, "A", "Chris");
		Parent p1 = getSameParent().replaceSibling(chris);
		Parent p2 = getSameParent();
		
		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		Assert.assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		Assert.assertEquals(chris, entry.getValue1());
		Assert.assertEquals(null, entry.getValue2());
		Assert.assertEquals("sibling", entry.getPath().getPathString(">"));
		Assert.assertEquals(Action.MODIFY, entry.getAction());
	}
}
