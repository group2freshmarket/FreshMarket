<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<li class="nav-item active" style="color: white;">Review Order</li>
			</ul>
		</nav>

		<h2>Order for ${user.name}...</h2>

		<table width="100%" border="1">
			<thead>
				<tr>
					<th>Item Image</th>
					<th>Item Name</th>
					<th>Item Price</th>
					<th>Item Quantity</th>
					<th>Item Total</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="i" items="${itemList}" varStatus="status">
					<tr>
						<td><img
							src='data:image/jpg;base64,<c:out value = " ${imageList[status.index]}"/>' /></td>
						<td><c:out value="${i.itemName}" /></td>
						<td><c:out value="${i.itemPrice}" /></td>
						<td><c:out value="${quantityList[status.index]}" /></td>
						<td><c:out value="${i.itemPrice * quantityList[status.index]}" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan='4' align="right">Sub-Total</td>
					<td><c:out value="${sub}" /></td>
				</tr>
				<tr>
					<td colspan='4' align="right">Tax</td>
					<td><c:out value="${tax}" /></td>
				</tr>
				<tr>
					<td colspan='4' align="right">Total</td>
					<td><c:out value="${tax+sub}" /></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>