package ru.onlineshop.goods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.dao.DaoFactory;
import ru.onlineshop.domain.goods.Catalog;
import ru.onlineshop.domain.goods.GoodsManager;
import ru.onlineshop.domain.goods.Group;


public class TestGoodsManager {
	Group testGroup = new Group("TestGroup", 0);
	DaoFactory daoFactory = DaoFactory.getInstance();

	@Test
	public final void testGoodsManagerCatalog() throws DAOException {
		GoodsManager goodsManager = new GoodsManager(daoFactory);
		assertNotNull(goodsManager.getCatalog());
	}

	@Test
	public final void testGoodsManagerallGoods() throws DAOException {
		GoodsManager goodsManager = new GoodsManager(daoFactory);
		assertNotNull(goodsManager.getAllGoods());
	}

	@Test
	public final void testGetAllGoods() throws DAOException {
		GoodsManager goodsManager = new GoodsManager(daoFactory);
		assertNotNull(goodsManager.getAllGoods());
	}

	@Test
	public final void testGetCatalog() throws DAOException {
		GoodsManager goodsManager = new GoodsManager(daoFactory);
		Catalog catalog = Catalog.getInstance();
		assertEquals(catalog, goodsManager.getCatalog());
	}

	@Test
	public final void testAddGroupNullGroup() throws DAOException {
		GoodsManager goodsManager = new GoodsManager(daoFactory);
		goodsManager.addGroup("ParentGroup", 0);
		boolean contains = false;
		for (Group group : goodsManager.getCatalog().getGroupList()) {
			if ("ParentGroup".equals(group.getName())) {
				contains = true;
				break;
			}
		}
		if (!contains) {
			fail("No group in catalog");
		}
	}	
}
