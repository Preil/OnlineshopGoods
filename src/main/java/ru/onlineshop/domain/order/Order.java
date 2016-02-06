package ru.onlineshop.domain.order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import ru.onlineshop.domain.ShopManagerImpl;


public class Order {
	private int id;
	private Date dateCreated;
	private int customerId;
	private String deliveryAddress;
	private ShippingType shippingType;
	private OrderStatus status;
	private List<OrderLine> goods;

	private static Logger log = Logger.getLogger(ShopManagerImpl.class.getName());

	public Order(int customerId, String deliveryAddress, ShippingType shippingType,
			List<OrderLine> goods) {
		this.customerId = customerId;
		this.deliveryAddress = deliveryAddress;
		this.shippingType = shippingType;
		this.status = OrderStatus.NEW;
		this.goods = goods;
		log.trace("Order for customer " + customerId + "created");
	}

	public int getId() {
		return id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public int getCustomerId() {
		return customerId;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public ShippingType getShippingType() {
		return shippingType;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public List<OrderLine> getGoods() {
		return goods;
	}

	public void setStatus(OrderStatus status) {
		if (null == status) {
			log.debug("Order status = null");
			throw new IllegalArgumentException();
		}
		this.status = status;
		log.trace("Status set to " + status);
	}

	public void setId(int id) {
		this.id = id;
		log.trace("Id set to " + id);
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		log.trace("Date set to " + dateFormat.format(dateCreated));
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
		log.trace("Customer id set to " + customerId);
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
		log.trace("Delivery address set to " + deliveryAddress);
	}

	public void setShippingType(ShippingType shippingType) {
		this.shippingType = shippingType;
		log.trace("Shipping type set to " + shippingType);
	}

	public void setGoods(List<OrderLine> goods) {
		this.goods = goods;
		log.trace("Set goods");
	}
}
