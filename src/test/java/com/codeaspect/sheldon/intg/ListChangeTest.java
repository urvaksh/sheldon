package com.codeaspect.sheldon.intg;

import static com.codeaspect.sheldon.intg.Structure.*;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.codeaspect.sheldon.AuditChecker;
import com.codeaspect.sheldon.entity.Action;
import com.codeaspect.sheldon.entity.AuditChangeEntry;

public class ListChangeTest {

	private Parent getSameParent() {
		return new Parent("A", 1L);
	}

	@Test
	public void testListAddition() {
		Parent p1 = getSameParent();
		Parent p2 = getSameParent().addChild(new Child(1L, "1", "3"));

		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		Assert.assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		Assert.assertEquals(Action.CREATE, entry.getAction());
	}

	@Test
	public void testListAdditionWithoutGroup() {
		Parent p1 = getSameParent();
		Parent p2 = getSameParent().addChild(new Child(1L, "1", "3"));

		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2, "dummy");

		Assert.assertEquals(0, entries.size());
	}

	@Test
	public void testListDeletion() {
		Parent p1 = getSameParent().addChild(new Child(1L, "1", "3"));
		Parent p2 = getSameParent();

		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		Assert.assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		Assert.assertEquals(Action.DELETE, entry.getAction());
	}

	@Test
	public void testListChange() {
		Parent p1 = getSameParent().addChild(new Child(1L, "1", "2"));
		Parent p2 = getSameParent().addChild(new Child(1L, "1", "3"));

		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		Assert.assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		Assert.assertEquals("2", entry.getValue1());
		Assert.assertEquals("3", entry.getValue2());
		Assert.assertEquals("children.fld2", entry.getPath().getPathString());
		Assert.assertEquals(Action.MODIFY, entry.getAction());
	}

	@Test
	public void testLargeListChange() {
		Parent p1 = getSameParent().addChild(new Child(1L, "1", "2")).addChild(new Child(2L, "1", "2"))
				.addChild(new Child(3L, "1", "2")).addChild(new Child(4L, "1", "2"));
		Parent p2 = getSameParent().addChild(new Child(1L, "1", "2")).addChild(new Child(2L, "1", "2"))
				.addChild(new Child(3L, "1", "B")).addChild(new Child(4L, "1", "2"));

		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		Assert.assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		Assert.assertEquals("2", entry.getValue1());
		Assert.assertEquals("B", entry.getValue2());
		Assert.assertEquals("children.fld2", entry.getPath().getPathString());
		Assert.assertEquals(Action.MODIFY, entry.getAction());
	}

	@Test
	public void testListChangeForMultipleChanges() {
		Parent p1 = getSameParent().addChild(new Child(1L, "1", "A"));
		Parent p2 = getSameParent().addChild(new Child(1L, "2", "B"));

		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		Assert.assertEquals(2, entries.size());
	}

	@Test
	public void testListChangeWithGroup() {
		Parent p1 = getSameParent().addChild(new Child(1L, "1", "A"));
		Parent p2 = getSameParent().addChild(new Child(1L, "2", "B"));

		Assert.assertEquals(2, new AuditChecker<Parent>().checkObjects(p1, p2, "standard").size());
		Assert.assertEquals(1, new AuditChecker<Parent>().checkObjects(p1, p2, "wacky").size());
	}

}
