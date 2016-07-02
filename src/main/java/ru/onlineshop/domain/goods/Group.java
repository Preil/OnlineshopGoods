package ru.onlineshop.domain.goods;

import java.util.List;

import org.apache.log4j.Logger;
import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.dao.DaoFactory;
import ru.onlineshop.dao.GroupDao;


public class Group extends AbstractGroup {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private GroupDao groupDao = daoFactory.getGroupDao();
    private int id = 0;
    private String name;
    private int parentId;

    private static Logger log = Logger.getLogger(Group.class.getName());

    public Group(String name, int parentId) {
        this.name = name;
        this.parentId = id;
        log.trace("Group " + name + " created");
    }

    @Override
    public List<Group> getGroupList() throws DAOException {
        return groupDao.getAllSubgroups(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        log.trace("Set id to " + id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (null == name || "".equals(name)) {
            log.trace("name = null or empty");
            throw new IllegalArgumentException();
        }
        this.name = name;
        log.trace("Set name to " + name);
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        if (parentId < 0) {
            log.debug("parentId <= 0");
            throw new IllegalArgumentException();
        }
        this.parentId = parentId;
        log.trace("Set parent id to " + parentId);
    }
}
