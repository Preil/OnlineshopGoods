package ru.onlineshop.domain.order;

import org.apache.log4j.Logger;
import ru.onlineshop.domain.goods.Goods;


public class OrderLine {

	private Goods goods;
	private int amount;

	private static Logger log = Logger.getLogger(OrderLine.class.getName());

	public OrderLine(Goods goods, int amount) {
		this.goods = goods;
		this.amount = amount;
		log.trace("Order line item=" + goods.getName() + ", amount=" + amount + " created");
	}

	public Goods getItem() {
		return goods;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if (amount <= 0) {
			log.debug("amount <= 0");
			throw new IllegalArgumentException();
		}
		this.amount = amount;
		log.trace("Amount setted to " + amount);
	}

	public int getPrice() {
		return goods.getPrice() * amount;
	}
}
