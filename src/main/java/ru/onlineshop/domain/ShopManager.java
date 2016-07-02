package ru.onlineshop.domain;

import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.domain.exception.AuthorizationException;
import ru.onlineshop.domain.exception.EmptyCartException;
import ru.onlineshop.domain.exception.NoOrderFoundException;
import ru.onlineshop.domain.exception.OpenOrderException;
import ru.onlineshop.domain.goods.Catalog;
import ru.onlineshop.domain.goods.Goods;
import ru.onlineshop.domain.goods.Group;
import ru.onlineshop.domain.order.Order;
import ru.onlineshop.domain.order.OrderLine;
import ru.onlineshop.domain.order.ShippingType;
import java.util.List;


public interface ShopManager {

	Customer createAccount(String login, String password, String name, String email) throws DAOException;

	Customer authorization(String login, String password) throws DAOException;

	boolean isLogedIn();
	
	void logOut();

	void changePassword(String oldPassword, String newPassword) throws AuthorizationException, DAOException;

	void changeAddress(String newAddress) throws AuthorizationException, DAOException;

	void changePhone(String newPhone) throws AuthorizationException, DAOException;

	void changeEmail(String newEmail) throws AuthorizationException, DAOException;

	void changeCreditCardInfo(String newCreditCardInfo) throws AuthorizationException, DAOException;

	Customer getCurrentCustomer() throws AuthorizationException;
	
	List<OrderLine> getGoodsLines() throws AuthorizationException;

	void addGoodsToCart(int goodsId, int amount) throws AuthorizationException, DAOException;

	void removeGoodsFromCart(Goods goods) throws AuthorizationException;

	void clearCart() throws AuthorizationException;

	int getPriceGoodsInCart() throws AuthorizationException;

	Order createOrder(String deliveryAddress, ShippingType shippingType)
			throws AuthorizationException, OpenOrderException, EmptyCartException;

	Order getCurrentOrder() throws AuthorizationException;
	
	void cancelOrderCreation() throws AuthorizationException;

	void confirmOrder() throws NoOrderFoundException, AuthorizationException, DAOException;

	List<Order> getAllOrders() throws DAOException;
	
	List<Goods> getAllGoods() throws DAOException;

	List<Goods> getGoodsByName(String name) throws DAOException;
	
	Goods getGoodsById(int goodsId) throws DAOException;

	List<Goods> getGoodsByParam(int groupId, int lowerPrice, int topPrice) throws DAOException;

	Catalog getCatalog();

	Group addGroup(String name, int parentId) throws AuthorizationException, DAOException;

	Goods addGoods(String name, int price, int groupId, int amount) throws AuthorizationException, DAOException;

	void updateGoodsPrice(int goodsId, int newPrice) throws DAOException, AuthorizationException;
	
	void updateGoodsAmount(int goodsId, int newAmount) throws DAOException, AuthorizationException;
	
	void deleteGoods(int goodsId) throws DAOException, AuthorizationException;
	
	void deleteGroup(int groupId) throws DAOException, AuthorizationException;
	
	int getId();
	
	boolean isAdmin();
}
