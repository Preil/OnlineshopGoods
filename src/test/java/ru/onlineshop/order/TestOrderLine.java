package ru.onlineshop.order;


import org.junit.Before;
import org.junit.Test;
import ru.onlineshop.domain.goods.Goods;
import ru.onlineshop.domain.order.OrderLine;

import static org.junit.Assert.assertEquals;

public class TestOrderLine {
	Goods goods;
	OrderLine orderLine;
	
	@Before
	public void setUp() {
		goods = new Goods("Name", 100, 1, 15);
		orderLine = new OrderLine(goods, 5);
	}

	@Test
	public void testGetItem() {
		assertEquals(goods, orderLine.getItem());
	}

	@Test
	public void testGetAmount() {
		assertEquals(5, orderLine.getAmount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNegativeAmount() {
		orderLine.setAmount(-10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetZeroAmount() {
		orderLine.setAmount(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetToBigAmount() {
		orderLine.setAmount(20);
	}

	@Test
	public void testSetAmount() {
		orderLine.setAmount(7);
		assertEquals(7, orderLine.getAmount());
	}

	@Test
	public void testGetPrice() {
		assertEquals(500, orderLine.getPrice());
	}

}
