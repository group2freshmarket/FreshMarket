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
						<td><img src='data:image/jpg;base64,<c:out value = " ${imageList[status.index]}"/>' /></td>
						<td><c:out value="${i.itemName}" /></td>
						<td><fmt:formatNumber type="currency" value="${i.itemPrice}" /></td>
						<td><c:out value="${quantityList[status.index]}" /></td>
						<td><c:out
								value="${i.itemPrice * quantityList[status.index]}" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan='4' align="right">Sub-Total</td>
					<td><fmt:formatNumber type="currency" value="${sub}" /></td>
				</tr>
				<tr>
					<td colspan='4' align="right">Tax</td>
					<td><fmt:formatNumber type="currency" value="${tax}" /></td>
				</tr>
				<tr>
					<td colspan='4' align="right">Total</td>
					<td><fmt:formatNumber type="currency" value="${tax+sub}" /></td>
				</tr>
			</tbody>
		</table>
		<form action="review" method="post" style="float:right;">
			<input type="radio" id="delivery" name="method" value="delivery" />
			<label for="delivery">Delivery</label> <input type="radio"
				id="pickup" name="method" value="pickup" /> <label for="pickup">Pick-up (10% off total)</label> <br />
			<p>Select Time:</p>
			<select name="time">
				<option value="8am">8am</option>
				<option value="9am">9am</option>
				<option value="10am">10am</option>
				<option value="11am">11am</option>
				<option value="12pm">12pm</option>
				<option value="1pm">1pm</option>
				<option value="2pm">2pm</option>
				<option value="3pm">3pm</option>
				<option value="4pm">4pm</option>
				<option value="5pm">5pm</option>
				<option value="6pm">6pm</option>
				<option value="7pm">7pm</option>
			</select> <br />
			<br />
			<button type="submit">Submit</button>
		</form>
	</div>
</body>
</html>