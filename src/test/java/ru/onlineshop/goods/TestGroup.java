package ru.onlineshop.goods;


import org.junit.Test;
import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.domain.goods.Group;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestGroup {

	@Test
	public final void testGetListInitialize() throws DAOException {
		Group group = new Group("Test", 0);
		assertNotNull(group.getGroupList());
	}

	@Test
	public final void testSetGetParentId() {
		Group group = new Group("Test", 0);
		group.setParentId(2);
		assertEquals(2, group.getParentId());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetNegativeParentId() {
		Group group = new Group("Test", 0);
		group.setParentId(-2);
	}

	@Test
	public final void testSetGetId() {
		Group group = new Group("Test", 0);
		group.setId(25);
		assertEquals(25, group.getId());
	}

	@Test
	public final void testGetListIsEmpty() throws DAOException {
		Group group = new Group("Test", 0);
		assertTrue(group.getGroupList().isEmpty());
	}

	@Test
	public final void testGetName() {
		Group group = new Group("Test", 0);
		assertEquals("Test", group.getName());
	}

	@Test
	public final void testSetName() {
		Group group = new Group("Test", 0);
		group.setName("New Name");
		assertEquals("New Name", group.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetNullName() {
		Group group = new Group("Test", 0);
		group.setName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetBlankName() {
		Group group = new Group("Test", 0);
		group.setName("");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetGroupNullName() throws DAOException {
		Group group = new Group("Test", 0);
		group.getGroup(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetGroupBlankName() throws DAOException {
		Group group = new Group("Test", 0);
		group.getGroup("");
	}
}
