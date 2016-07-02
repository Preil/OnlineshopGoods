package ru.onlineshop.servlet;

import ru.onlineshop.domain.ShopManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int shopManagerId = (Integer) request.getSession().getAttribute("shopmanagerid");
		ShopManager shopManager = ShopManagerHandler.getShopManagerById(shopManagerId);
		shopManager.logOut();
		request.getSession().setAttribute("login", "false");
		request.getSession().invalidate();
		response.sendRedirect("index.jsp");
	}
}
