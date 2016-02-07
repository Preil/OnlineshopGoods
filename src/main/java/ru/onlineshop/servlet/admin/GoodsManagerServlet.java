package ru.onlineshop.servlet.admin;

import ru.onlineshop.dao.DAOException;
import ru.onlineshop.domain.ShopManager;
import ru.onlineshop.domain.exception.AuthorizationException;
import ru.onlineshop.servlet.ShopManagerHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/goodsmanager")
public class GoodsManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		int goodsId = Integer.parseInt(request.getParameter("id"));
		int shopManagerId = (Integer) request.getSession().getAttribute("shopmanagerid");
		try {
			ShopManager shopManager = ShopManagerHandler.getShopManagerById(shopManagerId);
			if (action.equals("delete")) {
				shopManager.deleteGoods(goodsId);
			}

		} catch (DAOException | AuthorizationException e) {
		}
		response.sendRedirect("admin/admin.jsp");
	}

}
