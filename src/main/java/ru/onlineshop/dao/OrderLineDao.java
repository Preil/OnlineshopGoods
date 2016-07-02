package ru.onlineshop.dao;

import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.domain.order.OrderLine;
import java.util.List;


public interface OrderLineDao {
	
	void create(int orderId, int goodsId, int amount) throws DAOException;

	List<OrderLine> getAll(int orderId) throws DAOException;
}
