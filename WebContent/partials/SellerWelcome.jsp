<%@page import="org.online.shopping.pagination.Pagination.SortOrder"%>
<%@page import="org.online.shopping.pagination.Pagination"%>
<%@page import="org.online.shopping.entity.Cart"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.online.shopping.entity.Product"%>
<%@page import="org.online.shopping.entity.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		String username = String.valueOf(request.getAttribute(User.USERNAME));
	%>
	<div align="right">
		<h3 align="center">
			Welcome
			<%=username%></h3>
		<form action="seller?action=displayproductpage&username=<%=username%>"
			method="POST">
			<button>Add Product</button>
		</form>
		<br>
		<form action="login?action=logout&name=<%=username%>" method="POST">
			<input type="submit" value="Logout">
		</form>
	</div>
	<%
		if (request.getAttribute("message") != null) {
	%>
	<h5 style="color: red">
		<%
			out.print(String.valueOf(request.getAttribute("message")));
			}
		%>
	</h5>
	<div>
		<table align="center" border="2">
			<tr>
				<th>NAME</th>
				<th>COST</th>
				<th>COLOUR</th>
				<th>IMAGE</th>
				<th>Quantity</th>
			</tr>
			<%
				Pagination pagination = (Pagination) request.getAttribute("pagination");
				int totalOrder = Integer.valueOf(request.getAttribute("totalorder").toString());

				if (request.getAttribute(User.PRODUCTS) != null) {
					@SuppressWarnings("unchecked")
					Collection<Product> products = (Collection<Product>) request.getAttribute(User.PRODUCTS);
					Iterator<Product> iterator = products.iterator();
					while (iterator.hasNext()) {
						Product product = iterator.next();
			%>
			<tr>
				<td><%=product.getName()%></td>
				<%
					int discount = product.getDiscount();
							if (discount != 0) {
				%>
				<td><strike><%=product.getCost()%></strike>
					<h4 style="color: green"><%=discount + "%  Discount"%></h4> <%=product.getCost() - ((product.getCost() * discount) / 100)%></td>
				<%
					} else {
				%>
				<td><%=product.getCost()%></td>
				<%
					}
				%>
				<td><%=product.getColour()%></td>
				<td><img src="<%=product.getImageURL()%>" align="middle"></td>
				<%
					if (product.getQuantity() <= 0) {
				%>
				<td><h5 style="color: red">Sold Out</h5></td>
				<%
					} else {
				%>
				<td><%=product.getQuantity()%></td>
				<%
					}
				%>
				<td><form
						action="seller?action=productupdatepage&username=<%=username%>"
						method="POST">
						<input type="submit" name="<%=product.getId()%>" value="Update">
					</form></td>
			</tr>
			<%
				}
				}
			%>
		</table>
	</div>


	<br>
	<div align="center">
		<%
			String sortKey = null;
			SortOrder sortOrder = null;
			for (Map.Entry<String, SortOrder> entry : pagination.sort.entrySet()) {
				sortKey = entry.getKey();
				sortOrder = entry.getValue();
			}
			if (pagination.getSkip() == 0) {
				if (pagination.getLimit() < totalOrder - pagination.getSkip()) {
		%>
		<button disabled="disabled">
			<strong>Previous</strong>
		</button>
		<br> <br>
		<form
			action="seller?action=displaysellerproducts&username=<%=username%>&limit=<%=pagination.getLimit()%>&skip=<%=pagination.getSkip() + pagination.getLimit()%>&sort=<%=sortKey%>&sortorder=<%=sortOrder%>"
			method="POST">
			<button>
				<strong>Next</strong>
			</button>
		</form>
		<%
			}
			} else if (pagination.getLimit() >= totalOrder - pagination.getSkip()) {
		%>
		<form
			action="seller?action=displaysellerproducts&username=<%=username%>&limit=<%=pagination.getLimit()%>&skip=<%=pagination.getSkip() - pagination.getLimit()%>&sort=<%=sortKey%>&sortorder=<%=sortOrder%>"
			method="POST">
			<button type="submit">
				<strong>Previous</strong>
			</button>
		</form>
		<br>
		<button disabled="disabled">
			<strong>Next</strong>
		</button>
		<br>
		<%
			} else {
		%>
		<form
			action="seller?action=displaysellerproducts&username=<%=username%>&limit=<%=pagination.getLimit()%>&skip=<%=pagination.getSkip() - pagination.getLimit()%>&sort=<%=sortKey%>&sortorder=<%=sortOrder%>"
			method="POST">
			<button type="submit">
				<strong>Previous</strong>
			</button>
		</form>
		<br>
		<form
			action="seller?action=displaysellerproducts&username=<%=username%>&limit=<%=pagination.getLimit()%>&skip=<%=pagination.getSkip() + pagination.getLimit()%>&sort=<%=sortKey%>&sortorder=<%=sortOrder%>"
			method="POST">
			<button>
				<strong>Next</strong>
			</button>
		</form>
		<%
			}
		%>
	</div>

</body>
</html>