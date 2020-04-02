<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</head>
<body>
	<div class="container-fluid">
		<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
			<ul class="navbar-nav">
				<li class="nav-item active"><a class="nav-link" href="home">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="account">Account</a></li>
				<li class="nav-item"><a class="nav-link" href="logout">LogOut</a></li>
			</ul>
		</nav>
		<h2 class="text-center">Items</h2>
		<form method="post" action="home">
			<table width="100%" border="1">
				<thead>
					<tr>
						<th>Item Image</th>
						<th>Item Name</th>
						<th>Item Type</th>
						<th>Item Description</th>
						<th>Item Price</th>
						<th>Quantity</th>
						<th>Add to Cart</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" items="${itemList}" varStatus="status">
						<tr>
							<td><img src='data:image/jpg;base64,<c:out value = " ${imageList[status.index]}"/>' /></td>
							<td><c:out value="${i.itemName}" /></td>
							<td><c:out value="${i.itemType}" /></td>
							<td><c:out value="${i.itemDesc}" /></td>
							<td><fmt:formatNumber type="currency" value="${i.itemPrice}" /></td>
							<td><select name="count${i.itemId}">
							<c:forEach var="j" begin="1" end="${i.itemCount}">
								<option value="${j}"><c:out value= "${j}" /></option>
							</c:forEach>
							</select></td>
							<td><input type="checkbox" name="${i.itemId}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<button type="submit">Submit</button>
		</form>
	</div>
</body>
</html>