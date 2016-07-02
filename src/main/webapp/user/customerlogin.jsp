<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="refresh" content="0;url=user/catalog.jsp" />
<title>Success!</title>
</head>
<body>
	<% session.setAttribute("login", "customer"); %>
	<br>
	<br>
	<br>
	<p align="center">Hello, <%= request.getSession().getAttribute("username") %></p>
	<br>
	<br>
	<br>
	<p align="center"> You will be redirected soon... </p>
</body>
</html>