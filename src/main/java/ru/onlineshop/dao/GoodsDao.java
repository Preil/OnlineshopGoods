package ru.onlineshop.dao;

import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.domain.goods.Goods;

import java.util.List;


public interface GoodsDao
{
	Goods create(String name, int price, int groupId, int amount) throws DAOException;

	Goods read(int goodsId) throws DAOException;

	void updatePrice(int goodsId, int price) throws DAOException;
	
	void updateAmount(int goodsId, int amount) throws DAOException;

	void delete(int goodsId) throws DAOException;

	List<Goods> getAll() throws DAOException;
	
	List<Goods> getGoodsByName(String name) throws DAOException;
	
	List<Goods> getGoodsByParam(int groupId, int lowerPrice, int topPrice) throws DAOException;
	
	List<Goods> getGroupGoods(int groupId) throws DAOException;

	List<Goods> getGoodsByPrice(int lowerPrice, int topPrice) throws DAOException;

	Goods getGoodsById(int goodsId) throws DAOException;
}
