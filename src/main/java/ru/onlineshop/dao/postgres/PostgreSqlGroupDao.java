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
import ru.onlineshop.dao.GroupDao;
import ru.onlineshop.domain.goods.Group;


public class PostgreSqlGroupDao implements GroupDao {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static Logger log = Logger.getLogger(PostgreSqlGroupDao.class.getName());

    @Override
    public Group create(String name, int parentId) throws DAOException {
        log.trace("Get parameters: name=" + name + ", parentId=" + parentId);
        String sql = "insert into groups (group_name, group_parent_id) values (?, ?)";

        Group tempGroup = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Open connection");
            connection = daoFactory.getConnection();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, parentId);
            preparedStatement.execute();

            log.trace("Get result set");
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            log.trace("Create Group to return");
            tempGroup = parseResultSet(resultSet);
        } catch (SQLException e) {
            log.warn("Cannot create Group", e);
            throw new DAOException("Cannot create group", e);
        } finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
        }
        log.trace("Returning group");
        return tempGroup;
    }

    @Override
    public Group read(int groupId) throws DAOException {
        log.trace("Get parameters: id=" + groupId);
        String sql = "select from groups where id = ?;";

        Group tempGroup = null;
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
            resultSet.next();
            log.trace("Create group to return");
            tempGroup = parseResultSet(resultSet);
            tempGroup.setId(resultSet.getInt("id"));

        } catch (SQLException e) {
            log.warn("Cannot create user", e);
            throw new DAOException("Cannot read group", e);
        } finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
        }
        log.trace("Returning group");
        return tempGroup;
    }

    @Override
    public void delete(int groupId) throws DAOException {
        log.trace("Get parameters: groupId=" + groupId);
        String sql = "delete from groups where id = ?;";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            log.trace("Open connection");
            connection = daoFactory.getConnection();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupId);
            preparedStatement.executeUpdate();
            log.info("Group " + groupId + " deleted");

        } catch (SQLException e) {
            log.warn("Cannot create user", e);
            throw new DAOException("Cannot delete group", e);
        } finally {
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
        }
    }

    @Override
    public List<Group> getAll() throws DAOException {
        List<Group> groups = new ArrayList<>();
        String sql = "select * from groups order by id;";

        Group tempGroup = null;
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
            log.trace("Reading groups from DB and add them to Set");
            while (resultSet.next()) {
                tempGroup = parseResultSet(resultSet);
                groups.add(tempGroup);
                log.trace("Group " + tempGroup.getId() + " added to set");
            }
        } catch (SQLException e) {
            log.warn("Cannot create user", e);
            throw new DAOException("Cannot get all root groups", e);
        } finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
        }
        log.trace("Return groups");
        return groups;
    }

    @Override
    public List<Group> getAllSubgroups(int groupId) throws DAOException {
        List<Group> groups = new ArrayList<>();
        String sql = "select * from groups where group_parent_id = ? order by id;";

        Group tempGroup = null;
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
            log.trace("Reading groups from DB and add them to Set");
            while (resultSet.next()) {
                tempGroup = parseResultSet(resultSet);
                groups.add(tempGroup);
                log.trace("Group " + tempGroup.getId() + " added to set");
            }

        } catch (SQLException e) {
            log.warn("Cannot create user", e);
            throw new DAOException("Cannot get all root groups", e);
        } finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(connection);
        }
        log.trace("Return groups");
        return groups;
    }

    private Group parseResultSet(ResultSet resultSet) throws SQLException {
        log.trace("Create group to add to the list");
        Group group = new Group(resultSet.getString("group_name"), resultSet.getInt("group_parent_id"));
        group.setId(resultSet.getInt("id"));
        return group;
    }
}
