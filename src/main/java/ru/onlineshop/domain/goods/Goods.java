package ru.onlineshop.domain.goods;

import org.apache.log4j.Logger;

public class Goods {
	private int id;
	private String name;
	private int price;
	private int groupId;
	private int amount;

	private static Logger log = Logger.getLogger(Goods.class.getName());

	public Goods(String name, int price, int groupId, int amount) {
		this.name = name;
		this.price = price;
		this.groupId = groupId;
		this.amount = amount;
		log.trace("Created goods: name=" + name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (null == name || name.isEmpty()) {
			log.debug("Name = null or empty");
			throw new IllegalArgumentException();
		}
		this.name = name;
		log.trace("Set name to " + name);
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		if (price <= 0) {
			log.debug("price <= 0");
			throw new IllegalArgumentException();
		}
		this.price = price;
		log.trace("Set price to " + price);
	}

	public int getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if (amount < 0) {
			log.debug("amount < 0");
			throw new IllegalArgumentException();
		}
		this.amount = amount;
		log.trace("Set amount to " + amount);
	}

	public void setId(int id) {
		if (id <= 0) {
			log.debug("id <= 0");
			throw new IllegalArgumentException();
		}
		this.id = id;
		log.trace("Set id to " + id);
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		if (groupId <= 0) {
			log.debug("groupId <= 0");
			throw new IllegalArgumentException();
		}
		this.groupId = groupId;
		log.trace("Set froupId to " + groupId);
	}

}
