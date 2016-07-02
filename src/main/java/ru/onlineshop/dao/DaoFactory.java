package ru.onlineshop.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.dao.exception.NoDBPropertiesException;
import ru.onlineshop.dao.postgres.*;

public class DaoFactory {

	private static DaoFactory daoFactory;
	private static String type;
	private String user;
	private String password;
	private String url;
	private String driver;
	private static Logger log = Logger.getLogger(DaoFactory.class.getName());

	private DaoFactory() {
		loadProperties();
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("Driver not found\n", e);
		}
	}

	public static DaoFactory getInstance() {
		if (null == daoFactory) {
			daoFactory = new DaoFactory();
		}
		return daoFactory;
	}

	public Connection getConnection() throws DAOException {
		log.trace("Driver manager get connection");
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DAOException("No connection to DB", e);
		}
	}

	public GroupDao getGroupDao() {
		if (type.equalsIgnoreCase("postgres")) {
			return new PostgreSqlGroupDao();
		} else {
			return new PostgreSqlGroupDao();
		}
	}

	public GoodsDao getGoodsDao() {
		if (type.equalsIgnoreCase("postgres")) {
			return new PostgreSqlGoodsDao();
		} else {
			return new PostgreSqlGoodsDao();
		}
	}

	public OrderDao getOrderDao() {
		if (type.equalsIgnoreCase("postgres")) {
			return new PostgreSqlOrderDao();
		} else {
			return new PostgreSqlOrderDao();
		}
	}

	public CustomerDao getCustomerDao() {
		if (type.equalsIgnoreCase("postgres")) {
			return new PostgreSqlCustomerDao();
		} else {
			return new PostgreSqlCustomerDao();
		}
	}

	public OrderLineDao getOrderLineDao() {
		if (type.equalsIgnoreCase("postgres")) {
			return new PostgreSqlOrderLineDao();
		} else {
			return new PostgreSqlOrderLineDao();
		}
	}

	private void loadProperties() {
		Properties properties = new Properties();
		try {
			properties.load(DaoFactory.class.getResourceAsStream("/db.properties"));
			type = properties.getProperty("type");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
			url = properties.getProperty("url");
			driver = properties.getProperty("driver");
		} catch (IOException e) {
			throw new NoDBPropertiesException("Can't read file", e);
		}
	}
}
