package ru.onlineshop.dao;

import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.domain.Customer;

public interface CustomerDao {

	Customer create(String login, String password, String name, String email) throws DAOException;

	Customer read(String login) throws DAOException;

	Customer update(String login, String password, String name, String address,
			String phone, String email, String creditCardInfo) throws DAOException ;

}
