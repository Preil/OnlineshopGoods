<%@page import="ru.onlineshop.domain.goods.Group"%>
<%@page import="ru.onlineshop.servlet.ShopManagerHandler"%>
<%@page import="ru.onlineshop.domain.ShopManager"%>
<%@page import="ru.onlineshop.domain.goods.Goods"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css" rel="stylesheet" type="text/css">
<title>Admin</title>
</head>
<body>
	<%
	    if (null == session.getAttribute("login")) {
	        response.sendRedirect("index.jsp");
	        response.setHeader("Location", "index.jsp");
	        return;
	    }
	    int shopManagerId = (Integer) request.getSession().getAttribute("shopmanagerid");
	    ShopManager shopManager = ShopManagerHandler.getShopManagerById(shopManagerId);
	    if (!shopManager.isAdmin()) {
	        response.sendError(404, "Page not found");
	    }
	%>
	<div class="navbar navbar-default navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-ex-collapse">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="admin.jsp"><span>OnlineShop</span><br></a>
			</div>
			<div class="collapse navbar-collapse" id="navbar-ex-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a>Hello, <%=shopManager.getCurrentCustomer().getName()%></a></li>
					<li class="active"><a href="admin.jsp">Home</a></li>
					<li><a href="addgoods.jsp">New Goods</a></li>
					<li><a href="addgroup.jsp">New Group</a></li>
					<li><a href="../logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-3">
					<ul class="list-group">
						<%
						    for (Group group : shopManager.getCatalog().getGroupList()) {
						        if ((null != request.getParameter("groupid"))
						                && (Integer.parseInt(request.getParameter("groupid")) == group.getId())
						                && (null == request.getParameter("subgroupid"))) {
						%>
						<li class="list-group-item active"><%=group.getName()%></li>
						<%
						    for (Group subGroup : group.getGroupList()) {
						%>
						<li class="list-group-item"><a href="admin.jsp?groupid=<%=group.getId()%>&subgroupid=<%=subGroup.getId()%>"> --- <%=subGroup.getName()%></a></li>
						<%
						    }

						        } else if ((null != request.getParameter("groupid"))
						                && (Integer.parseInt(request.getParameter("groupid")) == group.getId())
						                && (null != request.getParameter("subgroupid"))) {
						%>
						<li class="list-group-item"><a href="admin.jsp?groupid=<%=group.getId()%>"><%=group.getName()%></a></li>
						<%
						    for (Group subGroup : group.getGroupList()) {
						                if (Integer.parseInt(request.getParameter("subgroupid")) == subGroup
						                        .getId()) {
						%>
						<li class="list-group-item active">--<%=subGroup.getName()%></li>
						<%
						    } else {
						%>
						<li class="list-group-item"><a href="admin.jsp?groupid=<%=group.getId()%>&subgroupid=<%=subGroup.getId()%>"> --- <%=subGroup.getName()%></a></li>
						<%
						    }
						            }
						        } else {
						%>
						<li class="list-group-item"><a href="admin.jsp?groupid=<%=group.getId()%>"><%=group.getName()%></a></li>
						<%
						    }
						    }
						%>
					</ul>
					<p></p>
					<p></p>
				</div>
				<div class="col-md-9">
					<p></p>
					<table class="table">
						<thead>
							<tr>
								<th width="200">Name</th>
								<th>Amount</th>
								<th width="200">Price</th>
								<th>Delete</th>
							</tr>
						</thead>
						<%
						    if (null == request.getParameter("groupid")) {
						%>
						<tbody>
							<%
							    for (Goods good : shopManager.getAllGoods()) {
							%>
							<tr>
								<td><%=good.getName()%></td>
								<td align="center"><%=good.getAmount()%></td>
								<td align="center"><%=good.getPrice()%></td>
								<td>
									<form action="../goodsmanager" method="post">
										<input type="hidden" name="id" value="<%=good.getId()%>"> <input type="hidden" name="action" value="delete">
										<input align="middle" type="submit" value="Delete">
									</form>
								</td>
							</tr>
							<%
							    }
							%>
						</tbody>
						<%
						    } else {
						%>
						<tbody>
							<%
							    if (request.getParameter("subgroupid") != null) {
							            for (Goods good : shopManager.getGoodsByParam(
							                    Integer.parseInt(request.getParameter("subgroupid")), 0, 0)) {
							%>
							<tr>
								<td><%=good.getName()%></td>
								<td align="center"><%=good.getAmount()%></td>
								<td align="center"><%=good.getPrice()%></td>
								<td>
									<form action="../goodsmanager" method="post">
										<input type="hidden" name="id" value="<%=good.getId()%>"> <input type="hidden" name="action" value="delete">
										<input align="middle" type="submit" value="Delete">
									</form>
								</td>
							</tr>
							<%
							    }
							        } else {
							            for (Goods good : shopManager.getGoodsByParam(
							                    Integer.parseInt(request.getParameter("groupid")), 0, 0)) {
							%>
							<tr>
								<td><%=good.getName()%></td>
								<td align="center"><%=good.getAmount()%></td>
								<td align="center"><%=good.getPrice()%></td>
								<td>
									<form action="../goodsmanager" method="post">
										<input type="hidden" name="id" value="<%=good.getId()%>"> <input type="hidden" name="action" value="delete">
										<input align="middle" type="submit" value="Delete">
									</form>
								</td>
							</tr>
							<%
							    }
							%>
						</tbody>
						<%
						    }
						    }
						%>
					</table>
					<p></p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>