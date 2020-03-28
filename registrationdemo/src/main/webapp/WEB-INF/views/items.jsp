<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</head>
<body>

	<div class="container-fluid">
		<div class="col-md-offset-2 col-md-7">
			<div class="panel panel-info" align="center">
				<div class="panel-body">

					<h2>Add Item</h2>
			<form method="post" action="saveItem" enctype="multipart/form-data">
				<!-- 	Item Id : <input type="text" name="itemId"> <br> -->
					Item Name : <input type="text" name="itemName"> <br><br>
					Item Type : <input type="text" name="itemType"> <br><br>
					Item Image : <input type="file" name="itemImage"> <br><br>
					Item count : <input type="text" name="itemCount"> <br><br>
					Item Price : $<input type="text" name="itemPrice"> <br><br>
					Item Description : <input type="text" name="itemDesc"> <br><br>
					<input type="submit" value="Add Item"> 
					</form>
				</div>
			</div>
			<h2 class="text-center">Items List</h2>
			<table width="100%" border="1">
				<thead>
					<tr>
						<th>Item Id</th>
						<th>Item Name</th>
						<th>Item Type</th>
						<th>Item Count</th>
						<th>Item Price</th>
						<th>Item Description</th>
						<th>Item Image</th>
						<th>Update</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" items="${itemList}" varStatus="status">
						<tr>
							<td><c:out value="${i.itemId}" /></td>
							<td><c:out value="${i.itemName}" /></td>
							<td><c:out value="${i.itemType}" /></td>
							<td><c:out value="${i.itemCount}" /></td>
							<td><c:out value="${i.itemPrice}" /></td>
							<td><c:out value="${i.itemDesc}" /></td>
							<td><img
								src='data:image/jpg;base64,<c:out value = " ${imageList[status.index]}"/>' /></td>
							<td><a href='/FreshMarket/updateItem?itemId=${i.itemId}'>UPDATE
									ITEMS</a></td>


							<td><a href='/FreshMarket/deleteItem?itemId=${i.itemId}'>DELETE
									ITEMS</a></td>
							<td></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


		</div>
	</div>
</body>
</html>