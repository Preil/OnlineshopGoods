package ru.onlineshop;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.onlineshop.goods.TestCatalog;
import ru.onlineshop.goods.TestGoods;
import ru.onlineshop.goods.TestGoodsManager;
import ru.onlineshop.goods.TestGroup;
import ru.onlineshop.order.TestOrder;
import ru.onlineshop.order.TestOrderLine;
import ru.onlineshop.order.TestShoppingCart;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestGoodsManager.class, TestCatalog.class, TestGoods.class, TestGroup.class,
		TestOrderLine.class, TestCustomer.class, TestOrder.class, TestShoppingCart.class })

public class AllTests {

}
