package ru.onlineshop.domain.goods;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.dao.DaoFactory;
import ru.onlineshop.dao.GoodsDao;
import ru.onlineshop.dao.GroupDao;


public class GoodsManager {
	private Catalog catalog;
	private GoodsDao goodsDao;
	private GroupDao groupDao;

	private static Logger log = Logger.getLogger(GoodsManager.class.getName());

	public GoodsManager(DaoFactory daoFactory) throws DAOException {
		log.trace("Getting goodsDao instance");
		goodsDao = daoFactory.getGoodsDao();
		log.trace("Getting groupDao instance");
		groupDao = daoFactory.getGroupDao();
		log.debug("Getting catalog instance");
		catalog = Catalog.getInstance();
		log.info("GoodsManager initialized");
	}

	public List<Goods> getAllGoods() throws DAOException {
		log.debug("Getting all goods.");
		return goodsDao.getAll();

	}

	public List<Goods> getGoodsByName(String name) throws DAOException {
		log.debug("Getting goods by name = " + name);
		return goodsDao.getGoodsByName(name);
	}

	public Goods getGoodsById(int goodsId) throws DAOException {
		log.debug("Getting goods by id=" + goodsId);
		return goodsDao.getGoodsById(goodsId);
	}

	public List<Goods> getGoodsByParam(int groupId, int lowerPrice, int topPrice)
			throws DAOException {
		int thisTopPrice = topPrice == 0 ? Integer.MAX_VALUE : topPrice;

		if (groupId != 0 && lowerPrice == 0 && topPrice == 0) {
			log.debug("Getting goods from groupId=" + groupId);
			List<Group> groups = groupDao.getAllSubgroups(groupId);
			List<Goods> goods= new ArrayList<>();
			goods.addAll(goodsDao.getGroupGoods(groupId));
			for (Group group : groups) {
                goods.addAll(goodsDao.getGroupGoods(group.getId()));
            }
			return goods;
		} else if (groupId != 0) {
			log.debug("Getting goods from groupId=" + groupId + " and price from " + lowerPrice
					+ " to " + topPrice);
			return goodsDao.getGoodsByParam(groupId, lowerPrice, thisTopPrice);
		} else {
			log.debug("Geting goods by price from " + lowerPrice + " to " + topPrice);
			return goodsDao.getGoodsByPrice(lowerPrice, thisTopPrice);
		}
	}

	public Catalog getCatalog() {
		log.debug("Getting catalog");
		return catalog;
	}

	public Group addGroup(String name, int parentId) throws DAOException {
		log.debug("Adding group " + name + (0 == parentId ? "" : "as subgroup of " + parentId));
		return groupDao.create(name, parentId);
	}

	public void deleteGroup(int groupId) throws DAOException {
		log.debug("Deleting group " + groupId);
		groupDao.delete(groupId);
	}

	public void deleteGoods(int goodsId) throws DAOException {
		log.debug("Deleting goods " + goodsId);
		goodsDao.delete(goodsId);
	}

	public Goods addGoods(String name, int price, int groupId, int amount) throws DAOException {
		log.debug("Adding goods: name=" + name + ", price=" + price + ", groupId=" + groupId
				+ ", amount=" + amount);
		return goodsDao.create(name, price, groupId, amount);
	}

	public void updateGoodsPrice(int goodsId, int newPrice) throws DAOException {
		log.debug("Updating goods " + goodsId + " set price " + newPrice);
		goodsDao.updatePrice(goodsId, newPrice);
	}

	public void updateGoodsAmount(int goodsId, int newAmount) throws DAOException {
		log.debug("Updating goods " + goodsId + " set amount " + newAmount);
		goodsDao.updateAmount(goodsId, newAmount);
	}

}