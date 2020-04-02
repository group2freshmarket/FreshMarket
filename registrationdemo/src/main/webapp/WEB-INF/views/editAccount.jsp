<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Account</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</head>
<body>
	<div class="container-fluid">
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
			<ul class="navbar-nav">
				<li style="color:white;">Edit Account</li>
			</ul>
		</nav>
		<form:form action="editAccount" method="post" modelAttribute="user" cssClass="form-horizontal">
			<div class="form-group">
				<label for="email" class="col-md-3 control-label"> Email</label>
				<div class="col-md-9">
					<form:input path="email" value='<%=request.getParameter("${user.email}")%>' cssClass="form-control" readonly="true"/>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-md-3 control-label"> Name</label>
				<div class="col-md-9">
					<form:input path="name" value='<%=request.getParameter("${user.name}")%>' cssClass="form-control" />
				</div>
			</div>
			<div class="form-group">
				<label for="icode" class="col-md-3 control-label">User Type</label>
				<div class="col-md-9">
					<form:input path="userType" value='<%=request.getParameter("${user.userType}")%>' cssClass="form-control" />
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-md-3 control-label">Password</label>
				<div class="col-md-9">
					<form:input path="password" value='<%=request.getParameter("${user.password}")%>' cssClass="form-control" />
				</div>
			</div>
			<div class="form-group">
				<!-- Button -->
				<div class="col-md-offset-3 col-md-9">
					<form:button cssClass="btn btn-primary">Submit</form:button>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>