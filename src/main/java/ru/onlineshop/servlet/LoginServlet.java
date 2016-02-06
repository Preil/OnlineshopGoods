package com.mentat.onlineshop.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mentat.onlineshop.dao.DAOException;
import com.mentat.onlineshop.domain.ShopManager;
import com.mentat.onlineshop.domain.ShopManagerImpl;
import com.mentat.onlineshop.domain.exception.AuthorizationException;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (null == request.getParameter("login") || null == request.getParameter("password")
				|| request.getParameter("login").isEmpty()
				|| request.getParameter("password").isEmpty()) {
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		ShopManager shopManager = null;
		try {
			shopManager = new ShopManagerImpl();
			shopManager.authorization(login, password);
		} catch (IllegalArgumentException | DAOException e) {
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		// Saving shopManager link for future use
		ShopManagerHandler.getActiveShopManagers().add(shopManager);
		HttpSession session = request.getSession();
		session.setAttribute("shopmanagerid", shopManager.getId());
		try {
			session.setAttribute("username", shopManager.getCurrentCustomer().getName());
		} catch (AuthorizationException e) {
			e.printStackTrace();
		}
		if (login.equals("admin")) {
			getServletContext().getRequestDispatcher("/admin/adminlogin.jsp").forward(request, response);
		} else {
			getServletContext().getRequestDispatcher("/user/customerlogin.jsp").forward(request, response);
		}
	}

}
