<%@page import="org.online.shopping.entity.Product"%>
<%@page import="org.online.shopping.controller.util.SessionUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Online shopping</title>
</head>
<body>
	<%
		String username = SessionUtility.getLoggedInUser(session);
	%>
	<div align="right">
		<h3 align="center">
			Welcome
			<%=username%></h3>
		<form
			action="seller?action=displaysellerproducts&username=<%=username%>&limit=10&skip=0&sort=timestamp&sortorder=desc"
			method="POST">
			<button>Product Details</button>
		</form>
	</div>
	<div align="center">
		<form
			action="seller?action=addproduct&limit=10&skip=0&sort=timestamp&sortorder=desc"
			method="POST">
			<table>
				<tr>
					<td>Product Name:</td>
					<td><input type="text" name="name" required></td>
				</tr>
				<tr>
					<td>Cost:</td>
					<td><input type="text" name="cost" required></td>
				</tr>
				<tr>
					<td>Image URL:</td>
					<td><input type="url" name="image" required></td>
				</tr>
				<tr>
					<td>Quantity:</td>
					<td><input type="text" name="quantity" required></td>
				</tr>
				<tr>
					<td>Colour:</td>
					<td><input type="text" name="colour" required></td>
				</tr>
			</table>
			<br> <input type="submit" value="AddProduct" name="addproduct">
		</form>
		<br>
	</div>
</body>
</html>