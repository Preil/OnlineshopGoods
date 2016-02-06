package ru.onlineshop.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import ru.onlineshop.dao.CustomerDao;
import ru.onlineshop.dao.DAOException;
import ru.onlineshop.dao.DaoFactory;
import ru.onlineshop.domain.Customer;


public class PostgreSqlCustomerDao implements CustomerDao {
	private DaoFactory daoFactory = DaoFactory.getInstance();
	private static Logger log = Logger.getLogger(PostgreSqlCustomerDao.class.getName());

	@Override
	public Customer create(String login, String password, String name, String email) throws DAOException {
		log.info("Creating new customer with login=" + login);
		String sql = "insert into customers (login, password, name, email) values (?,?,?,?);";

		Customer customer = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, login);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, name);
				preparedStatement.setString(4, email);
				preparedStatement.execute();
				try {
					log.trace("Get result set");
					resultSet = preparedStatement.getGeneratedKeys();
					resultSet.next();
					log.trace("Create customer to return");
					customer = new Customer(resultSet.getString("login"), resultSet.getString("password"),
							resultSet.getString("name"), resultSet.getString("email"));
					customer.setAddress(resultSet.getString("address"));
					customer.setPhone(resultSet.getString("phone"));
					customer.setCreditCardInfo(resultSet.getString("credit_card"));
					customer.setId(resultSet.getInt("id"));
					log.info("Customer with login=" + login + " created!");
				} finally {
					try {
						resultSet.close();
						log.trace("result set closed");
					} catch (SQLException e) {
						log.warn("Cannot close result set", e);
					}
				}
			} finally {
				try {
					preparedStatement.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch (SQLException e) {
			log.warn("Cannot create user", e);
			throw new DAOException("Cannot create user", e);
		} finally {
			try {
				connection.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Returning customer");
		return customer;
	}

	@Override
	public Customer read(String login) throws DAOException {
		log.trace("Lookink for customer with login=" + login);
		String sql = "select * from customers where login = ?;";

		Customer customer = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, login);
				try {
					log.trace("Get result set");
					resultSet = preparedStatement.executeQuery();
					if (resultSet.next()) {
						log.trace("Create customer to return");
						customer = new Customer(resultSet.getString("login"), resultSet.getString("password"),
								resultSet.getString("name"), resultSet.getString("email"));
						customer.setAddress(resultSet.getString("address"));
						customer.setPhone(resultSet.getString("phone"));
						customer.setCreditCardInfo(resultSet.getString("credit_card"));
						customer.setId(resultSet.getInt("id"));
					}
				} finally {
					try {
						resultSet.close();
						log.trace("result set closed");
					} catch (SQLException e) {
						log.warn("Cannot close result set", e);
					}
				}
			} finally {
				try {
					preparedStatement.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot read user", e);
		} finally {
			try {
				connection.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}

		if (null == customer) {
			log.debug("Customer not found");
		} else {
			log.trace("Customer " + login + " found");
		}
		log.trace("Returning customer");
		return customer;
	}

	@Override
	public Customer update(String login, String password, String name, String address, String phone, String email,
			String creditCardInfo) throws DAOException {
		String sql = "update customers set password = ?, name = ?, address = ?, phone = ?, email = ?, credit_card = ? where login = ?";

		Customer customer = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, password);
				preparedStatement.setString(2, name);
				preparedStatement.setString(3, address);
				preparedStatement.setString(4, phone);
				preparedStatement.setString(5, email);
				preparedStatement.setString(6, creditCardInfo);
				preparedStatement.setString(7, login);
				preparedStatement.execute();

				try {
					log.trace("Get result set");
					resultSet = preparedStatement.getGeneratedKeys();
					resultSet.next();
					log.trace("Create customer to return");
					customer = new Customer(resultSet.getString("login"), resultSet.getString("password"),
							resultSet.getString("name"), resultSet.getString("email"));
					customer.setAddress(resultSet.getString("address"));
					customer.setPhone(resultSet.getString("phone"));
					customer.setCreditCardInfo(resultSet.getString("credit_card"));
					customer.setId(resultSet.getInt("id"));
				} finally {
					try {
						resultSet.close();
						log.trace("result set closed");
					} catch (SQLException e) {
						log.warn("Cannot close result set", e);
					}
				}
			} finally {
				try {
					preparedStatement.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot update user ", e);
		} finally {
			try {
				connection.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Customer " + login + " has updated info");
		log.trace("Returning updated customer");
		return customer;

	}
}