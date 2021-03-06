package ru.onlineshop.servlet.admin;

import ru.onlineshop.dao.exception.DAOException;
import ru.onlineshop.domain.ShopManager;
import ru.onlineshop.domain.exception.AuthorizationException;
import ru.onlineshop.servlet.ShopManagerHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/addgoods")
public class AddGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String goodsName = request.getParameter("goodsname");
		String groupName = request.getParameter("groupname");
		int price = Math.round(Float.parseFloat(request.getParameter("price"))*100);
		int amount = Integer.parseInt(request.getParameter("amount"));
		int shopManagerId = (Integer) request.getSession().getAttribute("shopmanagerid");

		ShopManager shopManager = ShopManagerHandler.getShopManagerById(shopManagerId);
		try {
			int groupId = 0;
			assert shopManager != null;
			if (null != shopManager.getCatalog().getGroup(groupName)){
				groupId = shopManager.getCatalog().getGroup(groupName).getId();
			}
			shopManager.addGoods(goodsName, price, groupId, amount);
		} catch (AuthorizationException | DAOException e) {
			e.printStackTrace();
		}
		response.sendRedirect("admin/admin.jsp");
	}

}
