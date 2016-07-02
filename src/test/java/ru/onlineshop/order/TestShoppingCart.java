package ru.onlineshop.order;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.onlineshop.domain.Customer;
import ru.onlineshop.domain.goods.Goods;
import ru.onlineshop.domain.goods.Group;
import ru.onlineshop.domain.order.Order;
import ru.onlineshop.domain.order.OrderStatus;
import ru.onlineshop.domain.order.ShippingType;
import ru.onlineshop.domain.order.ShoppingCart;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestShoppingCart {

	Customer mockCustomer;
	ShoppingCart shoppingCart;
	Group mockGroup;
	Goods mockGoods;
	Goods mockGoods1;
	
	@Before
	public void setUp() {
		mockCustomer = Mockito.mock(Customer.class);
		mockGroup = Mockito.mock(Group.class);
		Mockito.when(mockGroup.getId()).thenReturn(1);
		Mockito.when(mockCustomer.getId()).thenReturn(1);
		mockGoods = new Goods("Goods", 100, mockGroup.getId(), 10);
		mockGoods1 = new Goods("Goods1", 200, mockGroup.getId(), 15);
		shoppingCart = new ShoppingCart(mockCustomer);
	}
	
	@Test
	public void testGetId() {
		ShoppingCart shoppingCart1 = new ShoppingCart(mockCustomer);
		ShoppingCart shoppingCart2 = new ShoppingCart(mockCustomer);
		assertTrue(shoppingCart2.getId() - shoppingCart1.getId() == 1);
	}

	@Test
	public void testGetCustomer() {
		assertEquals(mockCustomer, shoppingCart.getCustomer());
	}

	@Test
	public void testGetGoodsLinesNotNull() {
		assertNotNull(shoppingCart.getGoodsLines());
	}

	@Test
	public void testGetGoodsLines() {
		assertTrue(shoppingCart.getGoodsLines().isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddGoodsNull() {
		shoppingCart.addGoods(null, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddGoodsNegativeAmountAndNull() {
		shoppingCart.addGoods(null, -5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddGoodsNegativeAmount() {
		shoppingCart.addGoods(mockGoods, -5);
	}

	@Test
	public void testAddGoods() {
		shoppingCart.addGoods(mockGoods, 5);
		assertEquals(mockGoods, shoppingCart.getGoodsLines().get(0).getItem());
	}

	@Test
	public void testRemoveGoods() {
		shoppingCart.addGoods(mockGoods, 5);
		shoppingCart.removeGoods(mockGoods);
		assertTrue(shoppingCart.getGoodsLines().isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveGoodsNull() {
		shoppingCart.addGoods(mockGoods, 5);
		shoppingCart.removeGoods(null);
	}

	@Test
	public void testClearCart() {
		shoppingCart.addGoods(mockGoods, 5);
		shoppingCart.addGoods(mockGoods1, 10);
		shoppingCart.clearCart();
		assertTrue(shoppingCart.getGoodsLines().isEmpty());
	}

	@Test
	public void testGetPrice() {
		shoppingCart.addGoods(mockGoods, 5);
		shoppingCart.addGoods(mockGoods1, 10);
		assertEquals(2500, shoppingCart.getPrice());
	}

	@Test
	public void testCreateOrder() {
		shoppingCart.addGoods(mockGoods, 5);
		Order order = shoppingCart.createOrder("Boston", ShippingType.AIR);
		assertEquals("Boston", order.getDeliveryAddress());
		assertEquals(ShippingType.AIR, order.getShippingType());
		assertTrue(order.getGoods().get(0).getItem().equals(mockGoods));
		assertEquals(OrderStatus.NEW, order.getStatus());
	}

}
