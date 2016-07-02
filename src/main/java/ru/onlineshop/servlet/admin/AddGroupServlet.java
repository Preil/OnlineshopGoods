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


@WebServlet("/addgroup")
public class AddGroupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String groupName = request.getParameter("groupname");
        String parentGroupName = request.getParameter("parentgroupname");
        int shopManagerId = (Integer) request.getSession().getAttribute("shopmanagerid");

        ShopManager shopManager = ShopManagerHandler.getShopManagerById(shopManagerId);
        try {
            int parentId = 0;
            if (null != shopManager.getCatalog().getGroup(parentGroupName)){
                parentId = shopManager.getCatalog().getGroup(parentGroupName).getId();
            }
            shopManager.addGroup(groupName, parentId);
        } catch (AuthorizationException | DAOException e) {
            e.printStackTrace();
        }
        response.sendRedirect("admin/admin.jsp");
    }

}
