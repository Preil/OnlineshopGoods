<%@page import="ru.onlineshop.domain.goods.Group"%>
<%@page import="ru.onlineshop.servlet.ShopManagerHandler"%>
<%@page import="ru.onlineshop.domain.ShopManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.3/js/bootstrap-select.min.js"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css" rel="stylesheet" type="text/css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.3/css/bootstrap-select.min.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript">
	$('#groupselect').selectpicker();
</script>
<title>Add Goods</title>
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
				<a class="navbar-brand" href="admin/admin.jsp"><span>OnlineShop</span><br></a>
			</div>
			<div class="collapse navbar-collapse" id="navbar-ex-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a>Hello, <%=shopManager.getCurrentCustomer().getName()%></a></li>
					<li><a href="admin.jsp">Home</a></li>
					<li class="active"><a href="addgoods.jsp">New Goods</a></li>
					<li><a href="addgroup.jsp">New Group</a></li>
					<li><a href="../logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<form role="form" action="addgoods" method="post">
						<div class="form-group">
							<label class="control-label" for="groupselect">Parent group selection:</label> <br /> <select id="groupselect"
								name="groupname" class="selectpicker form-group">
								<option selected disabled>Chose group:</option>
								<%
								    for (Group group : shopManager.getCatalog().getGroupList()) {
								%>
								<option><%=group.getName()%></option>
								<%
								    }
								%>
							</select>
						</div>
						<div class="form-group">
							<label class="control-label" for="goodsname">Enter goods name:</label> <input class="form-control" id="goodsname"
								name="goodsname" placeholder="Goods name" type="text">
						</div>
						<div class="form-group">
							<label class="control-label" for="price">Enter goods price:</label> <input class="form-control" id="price" name="price"
								placeholder="7845.27" type="text">
						</div>
						<div class="form-group">
							<label class="control-label" for="amount">Enter goods amount:</label> <input class="form-control" id="amount" name="amount"
								placeholder="100" type="text">
						</div>
						<button type="submit" class="btn btn-primary form-control">Add new Goods</button>
					</form>
				</div>
				<div class="col-md-4"></div>
			</div>
		</div>
	</div>
</body>
</html>