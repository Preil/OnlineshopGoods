package ru.onlineshop.servlet.admin;

import java.io.IOException;

@WebServlet("/goodsmanager")
public class GoodsManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
