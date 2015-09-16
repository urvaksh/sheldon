package com.codeaspect.sheldon.intg;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.codeaspect.sheldon.AuditChecker;
import com.codeaspect.sheldon.entity.Action;
import com.codeaspect.sheldon.entity.AuditChangeEntry;
import com.codeaspect.sheldon.intg.model.Child;
import com.codeaspect.sheldon.intg.model.Parent;

public class ListChangeTest {

	private Parent getSameParent() {
		return new Parent("A", 1L);
	}

	@Test
	public void testListAddition() {
		Parent p1 = getSameParent();
		Parent p2 = getSameParent().addChild(new Child(1L, "1", "3"));

		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		assertEquals(Action.CREATE, entry.getAction());
	}

	@Test
	public void testListAdditionWithoutGroup() {
		Parent p1 = getSameParent();
		Parent p2 = getSameParent().addChild(new Child(1L, "1", "3"));

		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2, "dummy");

		assertEquals(0, entries.size());
	}

	@Test
	public void testListDeletion() {
		Parent p1 = getSameParent().addChild(new Child(1L, "1", "3"));
		Parent p2 = getSameParent();

		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		assertEquals(Action.DELETE, entry.getAction());
	}

	@Test
	public void testListChange() {
		Parent p1 = getSameParent().addChild(new Child(1L, "1", "2"));
		Parent p2 = getSameParent().addChild(new Child(1L, "1", "3"));

		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		assertEquals("2", entry.getValue1());
		assertEquals("3", entry.getValue2());
		assertEquals("children.fld2", entry.getPath().getPathString());
		assertEquals(Action.MODIFY, entry.getAction());
	}

	@Test
	public void testLargeListChange() {
		Parent p1 = getSameParent().addChild(new Child(1L, "1", "2")).addChild(new Child(2L, "1", "2"))
				.addChild(new Child(3L, "1", "2")).addChild(new Child(4L, "1", "2"));
		Parent p2 = getSameParent().addChild(new Child(1L, "1", "2")).addChild(new Child(2L, "1", "2"))
				.addChild(new Child(3L, "1", "B")).addChild(new Child(4L, "1", "2"));

		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		assertEquals(1, entries.size());
		AuditChangeEntry entry = entries.get(0);
		assertEquals("2", entry.getValue1());
		assertEquals("B", entry.getValue2());
		assertEquals("children.fld2", entry.getPath().getPathString());
		assertEquals(Action.MODIFY, entry.getAction());
	}

	@Test
	public void testListChangeForMultipleChanges() {
		Parent p1 = getSameParent().addChild(new Child(1L, "1", "A"));
		Parent p2 = getSameParent().addChild(new Child(1L, "2", "B"));

		List<AuditChangeEntry> entries = new AuditChecker<Parent>().checkObjects(p1, p2);

		assertEquals(2, entries.size());
	}

	@Test
	public void testListChangeWithGroup() {
		Parent p1 = getSameParent().addChild(new Child(1L, "1", "A"));
		Parent p2 = getSameParent().addChild(new Child(1L, "2", "B"));

		assertEquals(2, new AuditChecker<Parent>().checkObjects(p1, p2, "standard").size());
		assertEquals(1, new AuditChecker<Parent>().checkObjects(p1, p2, "wacky").size());
	}

}
