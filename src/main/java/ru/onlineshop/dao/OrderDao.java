package ru.onlineshop.dao;

import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.domain.order.Order;
import ru.onlineshop.domain.order.ShippingType;

import java.util.List;


public interface OrderDao {
	public Order create(int customerId, String deliveryAddress, ShippingType shippingType) throws DAOException;

	public Order read(int id) throws DAOException;

	public List<Order> getAll(int customerId) throws DAOException;

}
