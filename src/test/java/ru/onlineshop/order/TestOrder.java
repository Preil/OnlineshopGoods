package ru.onlineshop.order;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.onlineshop.domain.Customer;
import ru.onlineshop.domain.order.Order;
import ru.onlineshop.domain.order.OrderLine;
import ru.onlineshop.domain.order.OrderStatus;
import ru.onlineshop.domain.order.ShippingType;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestOrder {
	
	Order order;
	@Mock
    Customer mockCustomer;
	@Mock
    List<OrderLine> mockGoods;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		Mockito.when(mockCustomer.getId()).thenReturn(1);
		order = new Order(mockCustomer.getId(), "SomeAddress", ShippingType.COURIER, mockGoods);
	}

	@Test
	public void testGetCustomer() {
		assertEquals(mockCustomer.getId(), order.getCustomerId());
	}

	@Test
	public void testGetDeliveryAddress() {
		assertEquals("SomeAddress", order.getDeliveryAddress());
	}

	@Test
	public void testGetShippingType() {
		assertEquals(ShippingType.COURIER, order.getShippingType());
	}

	@Test
	public void testGetStatus() {
		assertEquals(OrderStatus.NEW, order.getStatus());
	}

	@Test
	public void testGetGoods() {
		assertEquals(mockGoods, order.getGoods());
	}

	@Test
	public void testSetStatus() {
		order.setStatus(OrderStatus.CLOSED);
		assertEquals(OrderStatus.CLOSED, order.getStatus());
	}

	@Test
	public void testSetGetId() {
		order.setId(1);
		assertEquals(1, order.getId());
	}
	
	@Test
	public void testSetGetDate() {
		Date tempDate = new Date(435786458365L);
		order.setDateCreated(tempDate);
		assertEquals(tempDate, order.getDateCreated());
	}
	
	@Test
	public void testSetGetCustomerId() {
		order.setCustomerId(5);
		assertEquals(5, order.getCustomerId());
	}
	
	@Test
	public void testSetGetAddress() {
		order.setDeliveryAddress("new address");
		assertEquals("new address", order.getDeliveryAddress());
	}
	
	@Test
	public void testSetGetShippingType() {
		order.setShippingType(ShippingType.LAND);
		assertEquals(ShippingType.LAND, order.getShippingType());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetGetStatus() {
		order.setStatus(null);
	}
	
	@Test
	public void testSetGetGoods() {
		order.setGoods(mockGoods);
		assertEquals(mockGoods, order.getGoods());
	}
}
