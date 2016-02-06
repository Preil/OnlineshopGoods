package ru.onlineshop.domain.goods;

import java.util.List;

import org.apache.log4j.Logger;
import ru.onlineshop.dao.DAOException;
import ru.onlineshop.dao.DaoFactory;
import ru.onlineshop.dao.GroupDao;


public class Catalog extends AbstractGroup {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private GroupDao groupDao = daoFactory.getGroupDao();
	private static Catalog catalog;

	private static Logger log = Logger.getLogger(Catalog.class.getName());
	
	private Catalog() {
		log.trace("Catalog initialized");
	}
	
    @Override
    public List<Group> getGroupList() throws DAOException {
        return groupDao.getAllSubgroups(0);
    }
    
	public static Catalog getInstance() {
		if (null == catalog) {
			catalog = new Catalog();
		}
		return catalog;
	}

}
