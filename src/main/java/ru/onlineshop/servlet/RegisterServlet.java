package com.mentat.onlineshop.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mentat.onlineshop.dao.DAOException;
import com.mentat.onlineshop.domain.ShopManager;
import com.mentat.onlineshop.domain.ShopManagerImpl;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (null == request.getParameter("firstname") || null == request.getParameter("lastname")
				|| null == request.getParameter("login") || null == request.getParameter("password")) {
			getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		try {
			ShopManager shopManager = new ShopManagerImpl();
			shopManager.createAccount(login, password, firstName + " " + lastName, email);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
