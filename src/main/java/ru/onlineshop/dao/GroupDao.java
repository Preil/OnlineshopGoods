package ru.onlineshop.dao;

import ru.onlineshop.domain.goods.Group;

import java.util.List;


public interface GroupDao {
	Group create(String name, int parentId) throws DAOException;

	Group read(int id) throws DAOException;

	void delete(int groupId) throws DAOException;

	List<Group> getAll() throws DAOException;

    List<Group> getAllSubgroups(int groupId) throws DAOException;

}
