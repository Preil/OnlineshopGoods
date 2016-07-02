package ru.onlineshop;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.onlineshop.domain.Customer;
import ru.onlineshop.domain.order.Order;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestCustomer {

	Customer customer;
	
	@Before
	public void setUp() {
		customer = new Customer("Login", "Password", "Name", "Email");
	}

	@Test
	public void testGetLogin() {
		assertEquals("Login", customer.getLogin());
	}

	@Test
	public void testGetPassword() {
		assertEquals("Password", customer.getPassword());
	}

	@Test
	public void testGetName() {
		assertEquals("Name", customer.getName());
	}

	@Test
	public void testGetAddress() {
		assertEquals("Address", customer.getAddress());
	}

	@Test
	public void testGetPhone() {
		assertEquals("Phone", customer.getPhone());
	}

	@Test
	public void testGetEmail() {
		assertEquals("Email", customer.getEmail());
	}

	@Test
	public void testGetCreditCardInfo() {
		assertEquals("CreditCardInfo", customer.getCreditCardInfo());
	}

	@Test
	public void testGetOrders() {
		assertTrue(customer.getOrders().isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNullPassword() {
		customer.setPassword(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetBlankPassword() {
		customer.setPassword("");
	}

	@Test
	public void testSetPassword() {
		customer.setPassword("newPass");
		assertEquals("newPass", customer.getPassword());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNullAddress() {
		customer.setAddress(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetBlankAddress() {
		customer.setAddress("");
	}

	@Test
	public void testSetAddress() {
		customer.setAddress("newAddress");
		assertEquals("newAddress", customer.getAddress());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNullPhone() {
		customer.setPhone(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetBlankPhone() {
		customer.setPhone("");
	}

	@Test
	public void testSetPhone() {
		customer.setPhone("newPhone");
		assertEquals("newPhone", customer.getPhone());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNullEmail() {
		customer.setEmail(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetBlankEmail() {
		customer.setEmail("");
	}

	@Test
	public void testSetEmail() {
		customer.setEmail("newEmail");
		assertEquals("newEmail", customer.getEmail());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNullCreditCardInfo() {
		customer.setCreditCardInfo(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetBlankCreditCardInfo() {
		customer.setCreditCardInfo("");
	}

	@Test
	public void testSetCreditCardInfo() {
		customer.setCreditCardInfo("1234");
		assertEquals("1234", customer.getCreditCardInfo());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddNullOrder() {
		customer.addOrder(null);
	}
	
	@Test
	public void testAddOrder() {
		Order mockOrder = Mockito.mock(Order.class);
		customer.addOrder(mockOrder);
		assertTrue(customer.getOrders().contains(mockOrder));
	}

	@Test
	public void testSetGetLogin() {
		customer.setLogin("login");
		assertEquals("login", customer.getLogin());
	}

	@Test
	public void testSetGetName() {
		customer.setName("name");
		assertEquals("name", customer.getName());
	}

	@Test
	public void testSetGetId() {
		customer.setId(5);
		assertEquals(5, customer.getId());
	}

	@Test
	public void testSetGetOrders() {
		customer.setOrders(new ArrayList<Order>());
		assertTrue(customer.getOrders().isEmpty());
	}
}
