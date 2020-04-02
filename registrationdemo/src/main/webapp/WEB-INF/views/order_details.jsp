<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Review Order</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</head>
<body>
	<div class="container-fluid">
			<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
			<ul class="navbar-nav">
				<li style="color: white;">Order Details</li>
			</ul>
		</nav>
		<h2>Order from ${date}</h2>
		<table border="1">
			<tr>
				<th>Item Name</th>
				<th>Item Price</th>
				<th>Quantity</th>
			</tr>
			<c:forEach var="i" items="${details}" varStatus="status">
				<tr>
					<td><c:out value="${names[status.index]}" /></td>
					<td><fmt:formatNumber type="currency" value="${i.itemPrice}" /></td>
					<td><c:out value="${i.itemQty}" /></td>
				</tr>
			</c:forEach>
		</table>
		<a href="account">Back</a>
	</div>
</body>
</html>