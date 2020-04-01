<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Account</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</head>
<body>
	<div class="container-fluid">
		<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link" href="home">Home</a></li>
				<li class="nav-item"><a class="nav-link active" href="account">Account</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="logout">LogOut</a></li>
			</ul>
		</nav>
		<table class="table table-striped table-bordered">
			<tr>
				<td><b>Email </b>: ${user.email}</td>
			</tr>
			<tr>
				<td><b>Name </b> : ${user.name}</td>
			</tr>
			<tr>
				<td><b>Password </b> : ${user.password}</td>
			</tr>
			<tr>
				<td><b>User Type </b>: ${user.userType}</td>
			</tr>
		</table>
		<br />
		<form action="account" method="post">
			<table border="1">
				<tr>
					<th>Date</th>
					<th>Total Price</th>
					<th>Select</th>
				</tr>
				<c:forEach var="i" items="${purchases}" varStatus="status">
					<tr>
						<td><c:out value="${i.date}" /></td>
						<td><c:out value="${i.total_Price}" /></td>
						<td><input type="radio" name="select" value="${i.orderId}" />
					</tr>
				</c:forEach>
			</table>
			<button type="submit">Submit</button>
		</form>
	</div>
</body>
</html>