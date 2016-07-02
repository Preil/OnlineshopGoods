package ru.onlineshop.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.dao.DaoFactory;
import ru.onlineshop.dao.OrderLineDao;
import ru.onlineshop.domain.goods.Goods;
import ru.onlineshop.domain.order.OrderLine;


public class PostgreSqlOrderLineDao implements OrderLineDao {

	private DaoFactory daoFactory = DaoFactory.getInstance();

	private static Logger log = Logger.getLogger(PostgreSqlGroupDao.class.getName());

	@Override
	public void create(int orderId, int goodsId, int amount) throws DAOException {
		log.trace("Get parameters: orderId=" + orderId + ", goodsId=" + goodsId + ", amount=" + amount);
		String sql = "insert into order_lines (order_id, goods_id, amount) values (?,?,?)";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
				log.trace("Create prepared statement");
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, orderId);
				preparedStatement.setInt(2, goodsId);
				preparedStatement.setInt(3, amount);
				preparedStatement.executeUpdate();
				log.trace("OrderLine for order=" + orderId + " created");
		} catch (SQLException e) {
			log.warn("Cannot create user", e);
			throw new DAOException("Cannot create order line", e);
		} finally {
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
	}

	@Override
	public List<OrderLine> getAll(int orderId) throws DAOException {
		log.trace("Get parameters: orderId=" + orderId);
		List<OrderLine> orderLines = new ArrayList<>();
		String sql = "select * from order_lines where order_id = ? order by goods_id;";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
            log.trace("Open connection");
            connection = daoFactory.getConnection();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(sql);

            log.trace("Get result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int goodsId = resultSet.getInt("goods_id");
                log.trace("Creating goods for order line");
                Goods goods = new PostgreSqlGoodsDao().read(goodsId);
                log.trace("creating order line");
                OrderLine orderLine = new OrderLine(goods, resultSet.getInt("amount"));
                orderLines.add(orderLine);
                log.trace("Order line for goods " + goodsId + " added to list");
            }
		} catch (SQLException e) {
			log.warn("Cannot create user", e);
			throw new DAOException("Cannot get all order lines", e);
		} finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
		log.trace("Returning order lines");
		return orderLines;
	}
}
