package ru.onlineshop.dao;

import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.domain.Customer;

public interface CustomerDao {

	public Customer create(String login, String password, String name, String email) throws DAOException;

	public Customer read(String login) throws DAOException;

	public Customer update(String login, String password, String name, String address,
			String phone, String email, String creditCardInfo) throws DAOException ;

}
