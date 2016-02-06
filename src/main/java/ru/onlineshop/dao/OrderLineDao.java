package ru.onlineshop.dao;

import ru.onlineshop.domain.order.OrderLine;
import java.util.List;


public interface OrderLineDao {
	
	public void create(int orderId, int goodsId, int amount) throws DAOException;

	public List<OrderLine> getAll(int orderId) throws DAOException;
}
