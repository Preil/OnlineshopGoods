package ru.onlineshop.servlet.admin;

import ru.onlineshop.dao.DAOException;
import ru.onlineshop.domain.ShopManager;
import ru.onlineshop.domain.exception.AuthorizationException;

import java.io.IOException;



@WebServlet("/addgoods")
public class AddGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String goodsName = request.getParameter("goodsname");
		String groupName = request.getParameter("groupname");
		int price = Math.round(Float.parseFloat(request.getParameter("price"))*100);
		int amount = Integer.parseInt(request.getParameter("amount"));
		int shopManagerId = (Integer) request.getSession().getAttribute("shopmanagerid");
		
		ShopManager shopManager = ShopManagerHandler.getShopManagerById(shopManagerId);
		try {
            int groupId = 0;
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
