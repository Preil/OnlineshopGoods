package ru.onlineshop.goods;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.onlineshop.domain.goods.Goods;
import ru.onlineshop.domain.goods.Group;

import static org.junit.Assert.assertEquals;

public class TestGoods {

	@Mock
	private Group mockGroup;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(mockGroup.getId()).thenReturn(1);
	}

	@Test
	public final void testGetName() {
		Goods goods = new Goods("SomeName", 500, mockGroup.getId(), 5);
		assertEquals("SomeName", goods.getName());
	}

	@Test
	public final void testSetName() {
		Goods goods = new Goods("SomeName", 500, mockGroup.getId(), 5);
		goods.setName("NewName");
		assertEquals("NewName", goods.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetNullName() {
		Goods goods = new Goods("SomeName", 500, mockGroup.getId(), 5);
		goods.setName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetBlankName() {
		Goods goods = new Goods("SomeName", 500, mockGroup.getId(), 5);
		goods.setName("");
	}

	@Test
	public final void testGetPrice() {
		Goods goods = new Goods("SomeName", 500, mockGroup.getId(), 5);
		assertEquals(500, goods.getPrice());
	}

	@Test
	public final void testSetPrice() {
		Goods goods = new Goods("SomeName", 500, mockGroup.getId(), 5);
		goods.setPrice(1000);
		assertEquals(1000, goods.getPrice());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetZeroPrice() {
		Goods goods = new Goods("SomeName", 500, mockGroup.getId(), 5);
		goods.setPrice(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetnegativePrice() {
		Goods goods = new Goods("SomeName", 500, mockGroup.getId(), 5);
		goods.setPrice(-56);
	}

	@Test
	public final void testGetGroup() {
		Goods goods = new Goods("SomeName", 500, mockGroup.getId(), 5);
		assertEquals(mockGroup.getId(), goods.getGroupId());
	}

	@Test
	public final void testSetGroup() {
		Group tempGroup = Mockito.mock(Group.class);
		Mockito.when(tempGroup.getId()).thenReturn(1);
		Goods goods = new Goods("SomeName", 500, mockGroup.getId(), 5);
		goods.setGroupId(tempGroup.getId());
		assertEquals(tempGroup.getId(), goods.getGroupId());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetNegativeGroupId() {
		Goods goods = new Goods("SomeName", 500, mockGroup.getId(), 5);
		goods.setGroupId(-5);
	}
	
	@Test
	public final void testGoodsGetAmount() {
		Goods goods = new Goods("SomeName", 5123, mockGroup.getId(), 5);
		assertEquals(5, goods.getAmount());
	}
	
	@Test
	public final void testGoodsSetZeroAmount() {
		Goods goods = new Goods("SomeName", 5123, mockGroup.getId(), 0);
		assertEquals(0, goods.getAmount());
	}
	
	@Test
	public final void testGoodsSetAmount() {
		Goods goods = new Goods("SomeName", 5123, mockGroup.getId(), 5);
		goods.setAmount(10);
		assertEquals(10, goods.getAmount());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testGoodsSetNegativeAmount() {
		Goods goods = new Goods("SomeName", 5123, mockGroup.getId(), 5);
		goods.setAmount(-10);
	}
	
	@Test
	public final void testGoodsGetId() {
		Goods goods = Mockito.mock(Goods.class);
		Mockito.when(goods.getId()).thenReturn(1);
		assertEquals(1, goods.getId());
	}
	
	@Test
	public final void testGoodsSetId() {
		Goods goods = new Goods("SomeName", 5123, mockGroup.getId(), 5);
		goods.setId(1);
		assertEquals(1, goods.getId());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testGoodsSetnegativeId() {
		Goods goods = new Goods("SomeName", 5123, mockGroup.getId(), 5);
		goods.setId(-1);
	}
	

}
