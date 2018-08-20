<%@page import="org.online.shopping.pagination.Pagination.SortOrder"%>
<%@page import="org.online.shopping.pagination.Pagination"%>
<%@page import="java.util.Map"%>
<%@page import="org.online.shopping.entity.Cart"%>
<%@page import="org.online.shopping.entity.User"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.online.shopping.entity.Product"%>
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
		int quantity = (Integer) request.getAttribute(User.CART_QUANTITY);
		String name = String.valueOf(request.getAttribute(User.NAME));
	%>
	<div align="right">
		<form action="Account.jsp?username=<%=name%>" method="POST">
			<button>MyAccount</button>
		</form>
		<br>
		<form
			action="orders?action=openorderpage&username=<%=name%>&limit=10&skip=0&sort=order_timestamp&sortorder=desc"
			method="POST">
			<button>MY ORDERS</button>
		</form>
		<br> <br>
		<form action="cart?action=opencart&name=<%=name%>" method="POST">
			<input type="submit" value="CART(<%=quantity%>)">
		</form>
		<br> <br>
		<form action="login?action=logout&name=<%=name%>" method="POST">
			<input type="submit" value="Logout">
		</form>
		<h3 align="center">
			Welcome
			<%=name%></h3>
	</div>
	<div>
		<table align="center" border="2">
			<tr>
				<th>NAME</th>
				<th>COST</th>
				<th>COLOUR</th>
				<th>IMAGE</th>
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
				<td>
					<%
						if (request.getAttribute(Product.MESSAGE) != null
										&& product.getId() == request.getAttribute(Product.PRODUCT_ID)) {
					%>
					<h4 style="color: red"><%=request.getAttribute(Product.MESSAGE)%></h4>
					<%
						} else if (product.getQuantity() == 0) {
					%>
					<h4 style="color: red"><%="Not Available"%></h4>
					<form
						action="products?action=addproducttonotify&username=<%=name%>&id=<%=product.getId()%>&limit=10&skip=0&sort=timestamp&sortorder=desc"
						method="POST">
						<%
							if (product.isNotify()) {
						%>
						<h4>Notification Set</h4>
						<%
							} else {
						%>
						<button>Notify</button>
						<%
							}
						%>
					</form> <br> <%
 	}
 %>
					<form
						action="cart?action=addtocart&name=<%=request.getAttribute(User.NAME)%>&id=<%=product.getId()%>&limit=10&skip=0&sort=timestamp&sortorder=desc"
						method="POST">
						<button>ADD TO CART</button>
					</form>
				</td>
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
			action="products?action=displaybuyerproducts&name=<%=request.getAttribute("name")%>&limit=<%=pagination.getLimit()%>&skip=<%=pagination.getSkip() + pagination.getLimit()%>&sort=<%=sortKey%>&sortorder=<%=sortOrder%>"
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
			action="products?action=displaybuyerproducts&name=<%=request.getAttribute("name")%>&limit=<%=pagination.getLimit()%>&skip=<%=pagination.getSkip() - pagination.getLimit()%>&sort=<%=sortKey%>&sortorder=<%=sortOrder%>"
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
			action="products?action=displaybuyerproducts&name=<%=request.getAttribute("name")%>&limit=<%=pagination.getSkip() - pagination.getLimit()%>&skip=<%=pagination.getSkip()%>&sort=<%=sortKey%>&sortorder=<%=sortOrder%>"
			method="POST">
			<button type="submit">
				<strong>Previous</strong>
			</button>
		</form>
		<br>
		<form
			action="products?action=displaybuyerproducts&name=<%=request.getAttribute("name")%>&limit=<%=pagination.getLimit()%>&skip=<%=pagination.getSkip() + pagination.getLimit()%>&sort=<%=sortKey%>&sortorder=<%=sortOrder%>"
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