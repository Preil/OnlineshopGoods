package ru.onlineshop.domain.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import ru.onlineshop.domain.Customer;
import ru.onlineshop.domain.goods.Goods;


public class ShoppingCart {
	private static int count = 0;
	private int id;
	private Customer customer;
	private List<OrderLine> goodlLines;

	private static Logger log = Logger.getLogger(ShoppingCart.class.getName());

	public ShoppingCart(Customer customer) {
		this.id = ++count;
		this.customer = customer;
		this.goodlLines = new ArrayList<OrderLine>();
		log.trace("Created new shopping cart, id=" + id);
	}

	public int getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public List<OrderLine> getGoodsLines() {
		return goodlLines;
	}

	public void addGoods(Goods goods, int amount) {
		log.trace("Creating " + amount + " " + goods.getName() + " OrderLine");
		goodlLines.add(new OrderLine(goods, amount));
		log.debug("Added " + amount + goods.getName() + " to cart " + id);
	}

	public void removeGoods(Goods goods) {
		if (null == goods) {
			log.debug("goods = null");
			throw new IllegalArgumentException();
		}
		log.trace("Looking for goods to remove");
		for (int i = 0; i < goodlLines.size(); i++) {
			if (goodlLines.get(i).getItem().equals(goods)) {
				log.trace("Goods " + goods.getName() + " found");
				goodlLines.remove(i);
				log.trace("Goods " + goods.getName() + " removed from cart " + id);
			}
		}
	}

	public void clearCart() {
		log.trace("Clearing the cart");
		goodlLines.clear();
		log.trace("Cart " + id + " cleared.");
	}

	public int getPrice() {
		int result = 0;
		log.trace("Counting all lines prices");
		for (OrderLine orderLine : goodlLines) {
			log.trace("Adding " + orderLine.getPrice() + " for " + orderLine.getItem().getName());
			result += orderLine.getPrice();
		}
		log.trace("Get overall price for cart " + id + " = " + result);
		return result;
	}

	public Order createOrder(String deliveryAddress, ShippingType shippingType) {
		log.debug("Creating order from cart " + id);
		return new Order(customer.getId(), deliveryAddress, shippingType, goodlLines);
	}
}
