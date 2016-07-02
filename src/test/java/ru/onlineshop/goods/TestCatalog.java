package ru.onlineshop.goods;


import org.junit.Test;
import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.domain.goods.Catalog;
import ru.onlineshop.domain.goods.Group;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


public class TestCatalog {
	
	@Test
	public final void testGetListInitialize() throws DAOException {
		Catalog catalog = Catalog.getInstance();
		assertNotNull(catalog.getGroupList());
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testSetList() throws DAOException {
		Catalog catalog = Catalog.getInstance();
		List<Group> mockList = Mockito.mock(ArrayList.class);
		assertEquals(mockList, catalog.getGroupList());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetGroupNullName() throws DAOException {
		Catalog catalog = Catalog.getInstance();
		catalog.getGroup(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetGroupBlankName() throws DAOException {
		Catalog catalog = Catalog.getInstance();
		catalog.getGroup("");
	}

}
