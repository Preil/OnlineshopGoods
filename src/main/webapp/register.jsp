<%--
  Created by IntelliJ IDEA.
  User: evgeniy
  Date: 07.02.16
  Time: 19:03
  To change this template use File | Settings | File Templates.
--%>
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
    <title>Registration</title>
</head>
<body>
<div class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-ex-collapse">
                <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><span>OnlineShop</span><br></a>
        </div>
        <div class="collapse navbar-collapse" id="navbar-ex-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#">Contacts</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <form role="form" action="/onlineshop/register" method="post">
                    <div class="form-group">
                        <label class="control-label" for="firstnameInput">Firstname:</label>
                        <input class="form-control" id="firstnameInput" name="firstname" placeholder="Enter firstname" type="text">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="lastnameInput">Lastname:</label>
                        <input class="form-control" id="lastnameInput" name="lastname" placeholder="Enter lastname" type="text">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="emailInput">Email:</label>
                        <input class="form-control" id="emailInput" name="email" placeholder="Enter email" type="email">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="loginInput">Login:</label>
                        <input class="form-control" id="loginInput" name="login" placeholder="Enter login" type="text">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="passwordInput">Password:</label>
                        <input class="form-control" id="passwordInput" name="password" placeholder="Enter password" type="password">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>
            </div>
            <div class="col-md-4"></div>
        </div>
    </div>
</div>
</body>
</html>
