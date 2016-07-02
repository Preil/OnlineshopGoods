package ru.onlineshop.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.dao.DaoFactory;
import ru.onlineshop.dao.OrderDao;
import ru.onlineshop.domain.order.Order;
import ru.onlineshop.domain.order.OrderLine;
import ru.onlineshop.domain.order.OrderStatus;
import ru.onlineshop.domain.order.ShippingType;


public class PostgreSqlOrderDao implements OrderDao {

	private DaoFactory daoFactory = DaoFactory.getInstance();
	private static Logger log = Logger.getLogger(PostgreSqlGroupDao.class.getName());

	@Override
	public Order create(int customerId, String deliveryAddress, ShippingType shippingType) throws DAOException {
		log.trace("Get parameters: customerId=" + customerId + ", deliveryAddress=" + deliveryAddress
				+ ", shippingType=" + shippingType);
		String sql = "insert into orders (customer_id, delivery_address, shipping_type) values (?,?,?);";
		Order order = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
			log.trace("Create prepared statement");
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, customerId);
			preparedStatement.setString(2, deliveryAddress);
			preparedStatement.setString(3, shippingType.name());
			preparedStatement.execute();
			log.trace("Get result set");
			resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			/*List<OrderLine> goods = new ArrayList<>();
			order = new Order(resultSet.getInt("customer_id"), resultSet.getString("delivery_address"),
					ShippingType.valueOf(resultSet.getString("shipping_type")), goods);
			order.setId(resultSet.getInt("id"));*/
			order = parseResultSet(resultSet);
		} catch (SQLException e) {
			log.warn("Cannot create order", e);
			throw new DAOException("Cannot create order", e);
		} finally {
			JdbcUtils.closeQuietly(resultSet);
			JdbcUtils.closeQuietly(preparedStatement);
			JdbcUtils.closeQuietly(connection);
		}
		log.trace("Returning order");
		return order;
	}

	@Override
	public Order read(int orderId) throws DAOException {
		log.trace("Get parameters: id=" + orderId);
		String sql = "select from orders where id = ?;";

		Order order = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
			log.trace("Create prepared statement");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, orderId);

			log.trace("Get result set");
			resultSet = preparedStatement.executeQuery();
			order = parseResultSet(resultSet);
		} catch (SQLException e) {
			log.warn("Cannot create user", e);
			throw new DAOException("Cannot read order", e);
		} finally {
			JdbcUtils.closeQuietly(resultSet);
			JdbcUtils.closeQuietly(preparedStatement);
			JdbcUtils.closeQuietly(connection);
		}
		log.trace("Returning order");
		return order;
	}

	@Override
	public List<Order> getAll(int customerId) throws DAOException {
		log.trace("Get parameters: customerId=" + customerId);
		List<Order> orders = new ArrayList<>();
		String sql = "select * from orders where customer_id = ? order by create_date;";

		Order order = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
			log.trace("Create prepared statement");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerId);

			log.trace("Get result set");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				order = parseResultSet(resultSet);
				orders.add(order);
				log.trace("Add order " + order.getId() + " to list");
			}
		} catch (SQLException e) {
			log.warn("Cannot create user", e);
			throw new DAOException("Cannot get all orders", e);
		} finally {
			JdbcUtils.closeQuietly(resultSet);
			JdbcUtils.closeQuietly(preparedStatement);
			JdbcUtils.closeQuietly(connection);
		}
		log.trace("Return list orders");
		return orders;
	}

	private Order parseResultSet(ResultSet resultSet) throws SQLException {
		log.trace("Create order");
		Order order = new Order(resultSet.getInt("customer_id"), resultSet.getString("delivery_address"),
				ShippingType.valueOf(resultSet.getString("shipping_type")), new ArrayList<OrderLine>());
		order.setStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
		order.setDateCreated(resultSet.getDate("create_date"));
		order.setId(resultSet.getInt("id"));
		return order;
	}
}
