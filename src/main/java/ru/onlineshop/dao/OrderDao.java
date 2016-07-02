package ru.onlineshop.dao;

import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.domain.order.Order;
import ru.onlineshop.domain.order.ShippingType;

import java.util.List;


public interface OrderDao {

	Order create(int customerId, String deliveryAddress, ShippingType shippingType) throws DAOException;

	Order read(int id) throws DAOException;

	List<Order> getAll(int customerId) throws DAOException;

}
