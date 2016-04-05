package ru.onlineshop.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import ru.onlineshop.dao.DAOException;
import ru.onlineshop.dao.DaoFactory;
import ru.onlineshop.dao.GoodsDao;
import ru.onlineshop.domain.goods.Goods;


public class PostgreSqlGoodsDao implements GoodsDao {
	private DaoFactory daoFactory = DaoFactory.getInstance();
	private static Logger log = Logger.getLogger(PostgreSqlGoodsDao.class.getName());

	@Override
	public Goods create(String name, int price, int groupId, int amount) throws DAOException {
		log.trace("Get parameters: name=" + name + ", price=" + price + ", groupId=" + groupId + ", amount=" + amount);
		String sql = "insert into goods (goods_name, price, amount, group_id) values (?, ?, ?, ?);";

		Goods tempGoods = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            preparedStatement.setInt(3, amount);
            preparedStatement.setInt(4, groupId);
            preparedStatement.execute();

            log.trace("Get result set");
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            log.trace("Create goods to return");
            tempGoods = new Goods(resultSet.getString("goods_name"),
                    resultSet.getInt("price"), resultSet.getInt("amount"),
                    resultSet.getInt("group_id"));
            tempGoods.setId(resultSet.getInt("id"));
		} catch (SQLException e) {
			log.warn("Cannot create goods", e);
			throw new DAOException("Cannot create goods", e);
		} finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
		log.trace("Goods created: id=" + tempGoods.getId() + ", name=" + tempGoods.getName()
				+ ", price=" + tempGoods.getPrice() + ", groupId=" + tempGoods.getGroupId()
				+ ", amount=" + tempGoods.getAmount());
		log.trace("Returning goods");
		return tempGoods;
	}

	@Override
	public Goods read(int goodsId) throws DAOException {
		String sql = "select * from goods where id = ?;";
		log.trace("Create prepared statement");

		Goods tempGoods = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, goodsId);
            log.trace("Get result set");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            log.trace("Create goods to return");
            tempGoods = new Goods(resultSet.getString("goods_name"),
                    resultSet.getInt("price"), resultSet.getInt("group_id"),
                    resultSet.getInt("amount"));
            tempGoods.setId(resultSet.getInt("id"));
		} catch (SQLException e) {
			throw new DAOException("Cannot read goods", e);
		} finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
		log.trace("Readed info about goods: id=" + tempGoods.getId());
		log.trace("Returning goods");
		return tempGoods;
	}

	@Override
	public void updatePrice(int goodsId, int price) throws DAOException {
		String sql = "update goods set price = ? where id = ?;";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
				log.trace("Create prepared statement");
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, price);
				preparedStatement.setInt(2, goodsId);
				preparedStatement.executeUpdate();
				log.trace("Price of goods id=" + goodsId + ", changed to " + price);

		} catch (SQLException e) {
			throw new DAOException("Cannot update goods price", e);
		} finally {
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
	}

	@Override
	public void updateAmount(int goodsId, int amount) throws DAOException {
		String sql = "update goods set amount = ? where id = ?;";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
				log.trace("Create prepared statement");
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, amount);
				preparedStatement.setInt(2, goodsId);
				preparedStatement.executeUpdate();
				log.trace("Amount of goods id=" + goodsId + ", changed to " + amount);
		} catch (SQLException e) {
			throw new DAOException("Cannot update goods amount", e);
		} finally {
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
	}

	@Override
	public void delete(int goodsId) throws DAOException {
		String sql = "delete from goods where id = ?;";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, goodsId);
            preparedStatement.executeUpdate();
            log.trace("Goods deleted id=" + goodsId);
		} catch (SQLException e) {
			throw new DAOException("Cannot delete goods", e);
		} finally {
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
	}

	@Override
	public List<Goods> getAll() throws DAOException {
		List<Goods> goods = new ArrayList<>();
		String sql = "select * from goods;";
		Goods tempGoods = null;
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
                log.trace("Create goods to add to the set");
                tempGoods = new Goods(resultSet.getString("goods_name"),
                        resultSet.getInt("price"), resultSet.getInt("group_id"),
                        resultSet.getInt("amount"));
                tempGoods.setId(resultSet.getInt("id"));
                goods.add(tempGoods);
                log.trace("Goods " + tempGoods.getId() + " added to set");
            }
		} catch (SQLException e) {
			throw new DAOException("Cannot get all goods", e);
		} finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
		log.trace("Returning goods");
		return goods;
	}

	@Override
	public List<Goods> getGoodsByName(String name) throws DAOException {
		List<Goods> goods = new ArrayList<>();
		String sql = "select * from goods where name = ?;";

		Goods tempGoods = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            log.trace("Get result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                log.trace("Create goods to add to the set");
                tempGoods = new Goods(resultSet.getString("goods_name"),
                        resultSet.getInt("price"), resultSet.getInt("amount"),
                        resultSet.getInt("group_id"));
                tempGoods.setId(resultSet.getInt("id"));
                goods.add(tempGoods);
                log.trace("Goods " + tempGoods.getId() + " added to set");
                }

		} catch (SQLException e) {
			throw new DAOException("Cannot get goods by name=" + name, e);
		} finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
		log.trace("Returning all goods with name=" + name);
		return goods;
	}

	@Override
	public Goods getGoodsById(int goodsId) throws DAOException {
		String sql = "select * from goods where id = ?;";

		Goods tempGoods = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, goodsId);
            log.trace("Get result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                log.trace("Create goods to add to the set");
                tempGoods = new Goods(resultSet.getString("goods_name"),
                        resultSet.getInt("price"), resultSet.getInt("amount"),
                        resultSet.getInt("group_id"));
                tempGoods.setId(resultSet.getInt("id"));
					}
		} catch (SQLException e) {
			throw new DAOException("Cannot get goods by id=" + goodsId, e);
		} finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
		log.trace("Returning goods with id=" + goodsId);
		return tempGoods;
	}

	@Override
	public List<Goods> getGoodsByParam(int groupId, int lowerPrice, int topPrice)
			throws DAOException {
		List<Goods> goods = new ArrayList<>();
		String sql = "select * from goods where group_id = ? and price <= ? and price >= ?;";

		Goods tempGoods = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
				log.trace("Create prepared statement");
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, groupId);
				preparedStatement.setInt(2, lowerPrice);
				preparedStatement.setInt(3, topPrice);

					log.trace("Get result set");
					resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						log.trace("Create goods to add to the set");
						tempGoods = new Goods(resultSet.getString("goods_name"),
								resultSet.getInt("price"), resultSet.getInt("amount"),
								resultSet.getInt("group_id"));
						tempGoods.setId(resultSet.getInt("id"));
						goods.add(tempGoods);
						log.trace("Goods " + tempGoods.getId() + " added to set");
					}
		} catch (SQLException e) {
			throw new DAOException("Cannot get goods from groupId=" + groupId + " and price from "
					+ lowerPrice + " to " + topPrice, e);
		} finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
		log.trace("Returning all goods from groupId=" + groupId + " and price from " + lowerPrice
				+ " to " + topPrice);
		return goods;
	}

	@Override
	public List<Goods> getGoodsByPrice(int lowerPrice, int topPrice) throws DAOException {
		List<Goods> goods = new ArrayList<>();
		String sql = "select * from goods where price <= ? and price >= ?;";

		Goods tempGoods = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, lowerPrice);
            preparedStatement.setInt(2, topPrice);

            log.trace("Get result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                log.trace("Create goods to add to the set");
                tempGoods = new Goods(resultSet.getString("goods_name"),
                        resultSet.getInt("price"), resultSet.getInt("amount"),
                        resultSet.getInt("group_id"));
                tempGoods.setId(resultSet.getInt("id"));
                goods.add(tempGoods);
                log.trace("Goods " + tempGoods.getId() + " added to set");
            }
		} catch (SQLException e) {
			throw new DAOException(
					"Cannot get goods where price from " + lowerPrice + " to " + topPrice, e);
		} finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
		log.trace("Returning all goods where price from " + lowerPrice + " to " + topPrice);
		return goods;
	}

	@Override
	public List<Goods> getGroupGoods(int groupId) throws DAOException {
		List<Goods> goods = new ArrayList<>();
		String sql = "select * from goods where group_id = ?;";
		Goods tempGoods = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			connection = daoFactory.getConnection();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupId);
            log.trace("Get result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                log.trace("Create goods to add to the set");
                tempGoods = new Goods(resultSet.getString("goods_name"),
                        resultSet.getInt("price"),
                        resultSet.getInt("group_id"), resultSet.getInt("amount"));
                tempGoods.setId(resultSet.getInt("id"));
                goods.add(tempGoods);
                log.trace("Goods " + tempGoods.getId() + " added to set");
            }
		} catch (SQLException e) {
			throw new DAOException("Cannot get goods by groupId=" + groupId, e);
		} finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
		}
		log.trace("Returning all goods from groupId=" + groupId);
		return goods;
	}
}
