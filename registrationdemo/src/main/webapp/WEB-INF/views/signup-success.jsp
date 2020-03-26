<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Account Details</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<%@ page isELIgnored="false"%>
</head>
<body>
	<div class="container-fluid">
		<div class="col-md-offset-2 col-md-7">
			<h1>${message}</h1>
			<hr />

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
			<a href="login">Login</a>
		</div>
	</div>
</body>
</html>
