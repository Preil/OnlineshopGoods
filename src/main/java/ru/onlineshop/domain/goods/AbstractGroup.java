package ru.onlineshop.domain.goods;

import ru.onlineshop.dao.DAOException;
import ru.onlineshop.dao.DaoFactory;
import ru.onlineshop.dao.GroupDao;

import java.util.List;


public abstract class AbstractGroup {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private GroupDao groupDao = daoFactory.getGroupDao();

    public List<Group> getGroupList() throws DAOException {
        return groupDao.getAll();
    }

    public Group getGroup(String name) throws DAOException {
        if (null == name || "".equals(name)) {
            throw new IllegalArgumentException();
        }
        Group resultGroup = null;
        for (Group group : getGroupList()) {
            if (resultGroup == null) {
            resultGroup = deepSearchingInGroupList(group, name);
            } else {
                return resultGroup;
            }
        }
        return resultGroup;
    }

    private Group deepSearchingInGroupList(Group group, String name) throws DAOException {
        Group resultGroup = null;
        if (group.getName().equalsIgnoreCase(name)) {
            return group;
        } else {
            for (Group tempGroup : group.getGroupList()) {
                System.out.println(group.getName());
                if (resultGroup == null) {
                    resultGroup = deepSearchingInGroupList(tempGroup, name);
                } else {
                    return resultGroup;
                }
            }
        }
        return resultGroup;
    }
}
