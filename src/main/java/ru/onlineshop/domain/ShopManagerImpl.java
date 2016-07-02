package ru.onlineshop.domain;

import java.util.List;

import org.apache.log4j.Logger;
import ru.onlineshop.dao.*;
import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.domain.exception.AuthorizationException;
import ru.onlineshop.domain.exception.EmptyCartException;
import ru.onlineshop.domain.exception.NoOrderFoundException;
import ru.onlineshop.domain.exception.OpenOrderException;
import ru.onlineshop.domain.goods.Catalog;
import ru.onlineshop.domain.goods.Goods;
import ru.onlineshop.domain.goods.GoodsManager;
import ru.onlineshop.domain.goods.Group;
import ru.onlineshop.domain.order.Order;
import ru.onlineshop.domain.order.OrderLine;
import ru.onlineshop.domain.order.ShippingType;
import ru.onlineshop.domain.order.ShoppingCart;


public class ShopManagerImpl implements ShopManager {
	private static int counter = 0;
	private int id;
	private Customer currentCustomer;
	private ShoppingCart shoppingCart;
	private Order order;
	private GoodsManager goodsManager;
	private DaoFactory daoFactory;
	private CustomerDao customerDao;
	private OrderDao orderDao;
	private OrderLineDao orderLineDao;

	private static Logger log = Logger.getLogger(ShopManagerImpl.class.getName());

	public ShopManagerImpl() throws DAOException {
		log.trace("Getting daoFactory instance");
		daoFactory = DaoFactory.getInstance();
		log.trace("Getting goodsManager instance");
		goodsManager = new GoodsManager(daoFactory);
		log.trace("Getting customerDao instance");
		customerDao = daoFactory.getCustomerDao();
		log.trace("Getting orderDao instance");
		orderDao = daoFactory.getOrderDao();
		log.trace("Getting orderLineDao instance");
		orderLineDao = daoFactory.getOrderLineDao();
		log.debug("ShopManagerImpl initialized");
		this.id = ++counter;
	}

	@Override
	public Customer createAccount(String login, String password, String name, String email) throws DAOException {
		log.debug("Creating customer " + login);
		return customerDao.create(login, password, name, email);
	}

	@Override
	public Customer authorization(String login, String password) {
		log.trace("Trying to authenticate as " + login);
		log.debug("Reading customer from DB");
		Customer tempCustomer = null;
		try {
			tempCustomer = customerDao.read(login);
		} catch (DAOException e) {
			log.warn("Customer not found");
		}

		if (null == tempCustomer) {
			log.warn("Trying to authenticate with no existing user login=" + login);
			throw new IllegalArgumentException("Wrong login or password!");
		}
		if (password.equals(tempCustomer.getPassword())) {
			currentCustomer = tempCustomer;
			shoppingCart = new ShoppingCart(currentCustomer);
			log.info("Customer " + login + " successfully logged");
			return tempCustomer;
		} else {
			log.warn("Authentication for customer=" + login + " failed. Wrong password");
			return null;
		}
	}

	@Override
	public boolean isLogedIn() {
		if (null == currentCustomer) {
			return false;
		}
		return true;
	}

	@Override
	public void logOut() {
		log.trace("Logging out customer " + currentCustomer.getLogin());
		currentCustomer = null;
		shoppingCart = null;
		order = null;
	}

	@Override
	public void changePassword(String oldPassword, String newPassword)
			throws AuthorizationException, DAOException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		if (null == oldPassword || null == newPassword || oldPassword.isEmpty()
				|| newPassword.isEmpty()) {
			log.trace("Some field is null or empty");
			throw new IllegalArgumentException("Can't be empty");
		}
		if (!currentCustomer.getPassword().equals(oldPassword)) {
			log.trace("Trying to change password, but entering wrong old.");
			throw new IllegalArgumentException("Old password is wrong");
		}
		if (currentCustomer.getPassword().equals(oldPassword) && oldPassword.equals(newPassword)) {
			log.trace("New pass is like the old");
			throw new IllegalArgumentException("New Password is like the old");
		}
		log.trace("Updating cutomer " + currentCustomer.getLogin() + " info");
		customerDao.update(currentCustomer.getLogin(), newPassword, currentCustomer.getName(),
				currentCustomer.getAddress(), currentCustomer.getPhone(),
				currentCustomer.getEmail(), currentCustomer.getCreditCardInfo());
		log.trace("Customer " + currentCustomer.getLogin() + " changed password.");
		log.trace("Setting new customer info");
		currentCustomer = customerDao.read(currentCustomer.getLogin());

	}

	@Override
	public void changeAddress(String newAddress) throws AuthorizationException, DAOException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		log.trace("Updating cutomer " + currentCustomer.getLogin() + " info");
		customerDao.update(currentCustomer.getLogin(), currentCustomer.getPassword(),
				currentCustomer.getName(), newAddress, currentCustomer.getPhone(),
				currentCustomer.getEmail(), currentCustomer.getCreditCardInfo());
		log.trace("Customer " + currentCustomer.getLogin() + " changed address.");
		log.trace("Setting new customer info");
		currentCustomer = customerDao.read(currentCustomer.getLogin());
	}

	@Override
	public void changePhone(String newPhone) throws AuthorizationException, DAOException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		log.trace("Updating cutomer " + currentCustomer.getLogin() + " info");
		customerDao.update(currentCustomer.getLogin(), currentCustomer.getPassword(),
				currentCustomer.getName(), currentCustomer.getAddress(), newPhone,
				currentCustomer.getEmail(), currentCustomer.getCreditCardInfo());
		log.trace("Customer " + currentCustomer.getLogin() + " changed phone.");
		log.trace("Setting new customer info");
		currentCustomer = customerDao.read(currentCustomer.getLogin());
	}

	@Override
	public void changeEmail(String newEmail) throws AuthorizationException, DAOException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		log.trace("Updating cutomer " + currentCustomer.getLogin() + " info");
		customerDao.update(currentCustomer.getLogin(), currentCustomer.getPassword(),
				currentCustomer.getName(), currentCustomer.getAddress(), currentCustomer.getPhone(),
				newEmail, currentCustomer.getCreditCardInfo());
		log.trace("Customer " + currentCustomer.getLogin() + " changed email.");
		log.trace("Setting new customer info");
		currentCustomer = customerDao.read(currentCustomer.getLogin());
	}

	@Override
	public void changeCreditCardInfo(String newCreditCardInfo)
			throws AuthorizationException, DAOException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		log.trace("Updating cutomer " + currentCustomer.getLogin() + " info");
		customerDao.update(currentCustomer.getLogin(), currentCustomer.getPassword(),
				currentCustomer.getName(), currentCustomer.getAddress(), currentCustomer.getPhone(),
				currentCustomer.getEmail(), newCreditCardInfo);
		log.trace("Customer " + currentCustomer.getLogin() + " changed credit card info.");
		log.trace("Setting new customer info");
		currentCustomer = customerDao.read(currentCustomer.getLogin());
	}

	@Override
	public Customer getCurrentCustomer() throws AuthorizationException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		log.trace("Returning currentCustomer");
		return currentCustomer;
	}

	@Override
	public List<OrderLine> getGoodsLines() throws AuthorizationException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		log.trace("Returning goodsLines");
		return shoppingCart.getGoodsLines();
	}

	@Override
	public void addGoodsToCart(int goodsId, int amount) throws AuthorizationException, DAOException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		log.trace("Adding " + amount + " " + goodsId + " to cart");
		shoppingCart.addGoods(getGoodsById(goodsId), amount);
	}

	@Override
	public void removeGoodsFromCart(Goods goods) throws AuthorizationException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		log.trace("Removing " + goods.getName() + " from cart");
		shoppingCart.removeGoods(goods);
	}

	@Override
	public void clearCart() throws AuthorizationException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		log.trace("Clearing the cart");
		shoppingCart.clearCart();
	}

	@Override
	public int getPriceGoodsInCart() throws AuthorizationException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		log.trace("Returning " + shoppingCart.getId() + " cart's overall price");
		return shoppingCart.getPrice();
	}

	@Override
	public Order createOrder(String deliveryAddress, ShippingType shippingType)
			throws AuthorizationException, OpenOrderException, EmptyCartException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		if (shoppingCart.getGoodsLines().isEmpty()) {
			log.trace("Try to create order when cart is empty");
			throw new EmptyCartException();
		}
		if (null != order) {
			log.trace("Trying to create order when another is open");
			throw new OpenOrderException();
		}
		log.debug("Creating order");
		order = shoppingCart.createOrder(deliveryAddress, shippingType);
		log.trace("Returning order");
		return order;
	}

	@Override
	public Order getCurrentOrder() throws AuthorizationException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		log.trace("Returning order");
		return order;
	}

	@Override
	public void cancelOrderCreation() throws AuthorizationException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		log.trace("Setting order to null");
		order = null;
		log.debug("Temp order canceled");
	}

	@Override
	public void confirmOrder() throws NoOrderFoundException, AuthorizationException, DAOException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		if (null == order) {
			log.debug("Order = null, no order to confirm");
			throw new NoOrderFoundException();
		}
		log.debug("Writing order to DB");
		Order createdOrder = orderDao.create(currentCustomer.getId(), order.getDeliveryAddress(),
				order.getShippingType());
		log.debug("Writing all goodsLines to DB");
		for (OrderLine orderLine : order.getGoods()) {
			log.trace("Writing " + orderLine.getAmount() + " " + orderLine.getItem().getName()
					+ " to DB");
			orderLineDao.create(createdOrder.getId(), orderLine.getItem().getId(),
					orderLine.getAmount());
		}
		log.trace("Setting order to null");
		shoppingCart = new ShoppingCart(currentCustomer);
		order = null;
	}

	@Override
	public List<Order> getAllOrders() throws DAOException {
		return orderDao.getAll(currentCustomer.getId());
	}

	@Override
	public List<Goods> getAllGoods() throws DAOException {
		log.trace("Returning all goods");
		return goodsManager.getAllGoods();
	}

	@Override
	public List<Goods> getGoodsByName(String name) throws DAOException {
		log.trace("Returning goods by name=" + name);
		return goodsManager.getGoodsByName(name);
	}

	@Override
	public Goods getGoodsById(int goodsId) throws DAOException {
		log.trace("Returning goods by id=" + goodsId);
		return goodsManager.getGoodsById(goodsId);
	}

	@Override
	public List<Goods> getGoodsByParam(int groupId, int lowerPrice, int topPrice)
			throws DAOException {
		log.trace("Returning goods by params: groupId=" + groupId + ", lowerPrice=" + lowerPrice
				+ ", topPrice=" + topPrice);
		return goodsManager.getGoodsByParam(groupId, lowerPrice, topPrice);
	}

	@Override
	public Catalog getCatalog() {
		log.trace("Returning catalog");
		return goodsManager.getCatalog();
	}

	@Override
	public Group addGroup(String name, int parentId) throws AuthorizationException, DAOException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		if (!currentCustomer.getLogin().equals("admin")) {
			log.warn("Try to add group from customer account");
			throw new AuthorizationException("Only administrator can do this =P");
		}
		log.debug("Adding group, name=" + name
				+ (0 == parentId ? "" : ", as subgroup of groupId=" + parentId));
		return goodsManager.addGroup(name, parentId);
	}

	@Override
	public Goods addGoods(String name, int price, int groupId, int amount)
			throws AuthorizationException, DAOException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		if (!currentCustomer.getLogin().equals("admin")) {
			log.warn("Try to add goods from customer account");
			throw new AuthorizationException("Only administrator can do this =P");
		}
		return goodsManager.addGoods(name, price, groupId, amount);
	}

	@Override
	public void updateGoodsPrice(int goodsId, int newPrice) throws DAOException, AuthorizationException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		if (!currentCustomer.getLogin().equals("admin")) {
			log.warn("Try to add goods from customer account");
			throw new AuthorizationException("Only administrator can do this =P");
		}
		goodsManager.updateGoodsPrice(goodsId, newPrice);
	}

	@Override
	public void updateGoodsAmount(int goodsId, int newAmount) throws DAOException, AuthorizationException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		if (!currentCustomer.getLogin().equals("admin")) {
			log.warn("Try to add goods from customer account");
			throw new AuthorizationException("Only administrator can do this =P");
		}
		goodsManager.updateGoodsAmount(goodsId, newAmount);
	}

	@Override
	public void deleteGoods(int goodsId) throws DAOException, AuthorizationException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		if (!currentCustomer.getLogin().equals("admin")) {
			log.warn("Try to add goods from customer account");
			throw new AuthorizationException("Only administrator can do this =P");
		}
		goodsManager.deleteGoods(goodsId);

	}

	@Override
	public void deleteGroup(int groupId) throws DAOException, AuthorizationException {
		if (null == currentCustomer) {
			log.trace("Not logged in");
			throw new AuthorizationException("You need to authorized first");
		}
		if (!currentCustomer.getLogin().equals("admin")) {
			log.warn("Try to add goods from customer account");
			throw new AuthorizationException("Only administrator can do this =P");
		}
		goodsManager.deleteGroup(groupId);

	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public boolean isAdmin() {
		return currentCustomer.getLogin().equals("admin");
	}

}
