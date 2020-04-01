<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Process Order</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</head>
<body>
	<div class="container-fluid">
		<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
			<ul class="navbar-nav">
				<li class="nav-item active" style="color: white;">Process Order</li>
			</ul>
		</nav>
	</div>
	<h2>Order for ${user}</h2>
	<h4>Method: ${method}</h4>
	<h4>Time: ${time}</h4>
	<h4>Discounts: ${discount}</h4>
	<h4>Total: ${total}</h4>
	<form method="post" action="process">
		Card Number: <input type="text" name="cardnumber"
			placeholder="#### - #### - #### - ####"> <br /> Expiry Date:
		<input type="text" name="exdate" placeholder="mm/yy" /> <br /> CVV:
		<input type="text" name="cvv" placeholder="###"> <br />
		<button type="submit">Submit</button>
	</form>
</body>
</html>