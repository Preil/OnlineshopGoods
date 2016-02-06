package com.mentat.onlineshop.web.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mentat.onlineshop.dao.DAOException;
import com.mentat.onlineshop.domain.ShopManager;
import com.mentat.onlineshop.domain.exception.AuthorizationException;
import com.mentat.onlineshop.domain.exception.EmptyCartException;
import com.mentat.onlineshop.domain.exception.NoOrderFoundException;
import com.mentat.onlineshop.domain.exception.OpenOrderException;
import com.mentat.onlineshop.domain.order.ShippingType;
import com.mentat.onlineshop.web.servlet.ShopManagerHandler;

@WebServlet("/order")
public class OrderHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String shippingTypeString = request.getParameter("shippingtype");
		String deliveryAddressString = request.getParameter("deliveryaddress");
		String orderActionString = request.getParameter("orderaction");
		int shopManagerId = (Integer) request.getSession().getAttribute("shopmanagerid");
		ShopManager shopManager = ShopManagerHandler.getShopManagerById(shopManagerId);
		try {
			if (orderActionString.equals("create")) {
				shopManager.createOrder(deliveryAddressString, ShippingType.valueOf(shippingTypeString.toUpperCase()));
				response.sendRedirect("user/neworder.jsp");
			} else if (orderActionString.equals("cancel")) {
				shopManager.cancelOrderCreation();
				response.sendRedirect("user/catalog.jsp");
			} else if (orderActionString.equals("confirm")) {
				shopManager.confirmOrder();
				response.sendRedirect("user/orders.jsp");
			}
		} catch (AuthorizationException | OpenOrderException | EmptyCartException | NoOrderFoundException | DAOException e) {
			e.printStackTrace();
		}
	}
}
