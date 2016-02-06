package ru.onlineshop.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import ru.onlineshop.domain.order.Order;


public class Customer {
	private int id;
	private String login;
	private String password;
	private String name;
	private String address;
	private String phone;
	private String email;
	private String creditCardInfo = "";
	private List<Order> orders = new ArrayList<Order>();

	private static Logger log = Logger.getLogger(Customer.class.getName());

	public Customer(String login, String password, String name, String email) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public void addOrder(Order order) {
		if (null == order) {
			log.debug("Order cannot be null");
			throw new IllegalArgumentException();
		}
		log.trace("Adding order " + order.getId() + ", to list");
		orders.add(order);
		log.trace("Order " + order.getId() + " added");
	}

	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getCreditCardInfo() {
		return creditCardInfo;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCreditCardInfo(String creditCardInfo) {
		this.creditCardInfo = creditCardInfo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
