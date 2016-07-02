<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="ru.onlineshop.servlet.ShopManagerHandler"%>
<%@page import="ru.onlineshop.domain.ShopManager"%>
<%@page import="ru.onlineshop.domain.order.OrderLine"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css" rel="stylesheet" type="text/css">
<title>Shopping cart</title>
</head>
<body>
	<%
	    if (null == session.getAttribute("login")) {
	        response.sendRedirect("index.jsp");
	        response.setHeader("Location", "index.jsp");
	        return;
	    }
	    int shopManagerId = (Integer) session.getAttribute("shopmanagerid");
	    ShopManager shopManager = ShopManagerHandler.getShopManagerById(shopManagerId);
	    List<OrderLine> orderLines = shopManager.getGoodsLines();
	%>
	<div class="navbar navbar-default navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-ex-collapse">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="catalog.jsp"><span>OnlineShop</span><br></a>
			</div>
			<div class="collapse navbar-collapse" id="navbar-ex-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a>Hello, <%=shopManager.getCurrentCustomer().getName()%></a></li>
					<li class="active"><a href="shoppingcart.jsp"> Shopping cart</a></li>
					<li><a href="orders.jsp"> Orders</a></li>
					<li><a href="catalog.jsp">Catalog</a></li>
					<li><a href="../logout"> Logout</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<table class="table">
						<thead>
							<tr>
								<th width="200">Name</th>
								<th>Amount</th>
								<th>Price</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<%
							    for (int i = 0; i < orderLines.size(); i++) {
							%>
							<tr>
								<td width="200"><%=orderLines.get(i).getItem().getName()%></td>
								<td><%=orderLines.get(i).getAmount()%></td>
								<td width="100"><%=orderLines.get(i).getPrice()%></td>
								<td><form action="../ShoppingCartManager" method="post">
										<input type="hidden" name="orderlineid" value="<%=i%>"> <input type="hidden" name="action" value="delete">
										<input type="submit" value="delete" />
									</form></td>
							</tr>
							<%
							    }
							%>
							<tr></tr>
							<tr>
								<td>Total:</td>
								<td></td>
								<td><%=shopManager.getPriceGoodsInCart()%></td>
								<td></td>
							</tr>
						</tbody>
					</table>

					<form action="../order" method="post">
						<span>Input delivery address:</span> <input class="form-control" type="text" name="deliveryaddress"> <span>Choose
							delivery type:</span> <select class="form-control" name="shippingtype">
							<option value="self">Self</option>
							<option value="courier">Courier</option>
							<option value="land">Land</option>
							<option value="ship">Ship</option>
							<option value="air">Air</option>
						</select> <br /> <input class="form-control" type="hidden" name="orderaction" value="create">
						<button type="submit" class="btn btn-primary form-control">Post order</button>
					</form>
				</div>
				<div class="col-md-4"></div>
			</div>
		</div>
	</div>
</body>
</html>