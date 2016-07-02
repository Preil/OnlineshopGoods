package ru.onlineshop.servlet.user;

import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.domain.ShopManager;
import ru.onlineshop.domain.exception.AuthorizationException;
import ru.onlineshop.domain.order.OrderLine;
import ru.onlineshop.servlet.ShopManagerHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ShoppingCartManager")
public class ShoppingCartManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String action = request.getParameter("action");
		String goodsIdString = request.getParameter("id");
		String orderLineIdString = request.getParameter("orderlineid");
		int shopManagerId = (Integer) request.getSession().getAttribute("shopmanagerid");

		try {
			ShopManager shopManager = ShopManagerHandler.getShopManagerById(shopManagerId);
			if (action.equals("order")) {
				boolean isFound = false;
				int goodsId = Integer.parseInt(goodsIdString);
				for (OrderLine orderLine : shopManager.getGoodsLines()) {
					if (orderLine.getItem().getId() == goodsId) {
						orderLine.setAmount(orderLine.getAmount() + 1);
						isFound = true;
						break;
					}
				}
				if (!isFound) {
					shopManager.addGoodsToCart(goodsId, 1);
				}
				response.sendRedirect("user/catalog.jsp");
			}
			if (action.equals("delete")) {
				shopManager.getGoodsLines().remove(Integer.parseInt(orderLineIdString));
				response.sendRedirect("user/shoppingcart.jsp");
			}
		} catch (DAOException | AuthorizationException e) {
		}
	}

}
