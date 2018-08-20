<%@page import="org.online.shopping.pagination.Pagination.SortOrder"%>
<%@page import="org.online.shopping.model.OrderProducts"%>
<%@page import="org.online.shopping.pagination.Pagination"%>
<%@page import="org.online.shopping.entity.Cart"%>
<%@page import="java.util.Map"%>
<%@page import="com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry"%>
<%@page import="org.online.shopping.entity.Order"%>
<%@page import="java.util.Collection"%>
<%@page import="org.online.shopping.entity.User"%>
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
		String name = request.getParameter(User.USERNAME);
		out.print("welcome" + name);
	%>
	<form
		action="products?action=displaybuyerproducts&name=<%=name%>&limit=10&skip=0&sort=timestamp&sortorder=desc"
		method="POST">
		<button type="submit">Product Details</button>
	</form>
	<br>

	<table align="center" border="2">
		<tr>
			<th>NAME</th>
			<th>COST</th>
			<th>COLOUR</th>
			<th>Quantity</th>
			<th>IMAGE</th>
			<th>OrderedOn</th>
		</tr>
		<%
			String previousOrderId = null;
			Pagination pagination = (Pagination) request.getAttribute("pagination");
			int totalOrder = Integer.valueOf(request.getAttribute("totalorder").toString());
			@SuppressWarnings("unchecked")
			Collection<OrderProducts> orderProducts = (Collection<OrderProducts>) request
					.getAttribute(OrderProducts.ORDER_PRODUCTS);
			Iterator<OrderProducts> iterator = orderProducts.iterator();
			while (iterator.hasNext()) {
				int sum = 0;
				OrderProducts orderProduct = iterator.next();
				String orderId = orderProduct.getOrderId();
				if (!orderId.equals(previousOrderId)) {
					previousOrderId = orderId;
		%>
		<tr>
			<th>OrderID: <%=orderId%></th>
		</tr>
		<%
			}

				Product product = orderProduct.getProduct();
		%>
		<tr>
			<td><%=product.getName()%></td>
			<%
				int discount = product.getDiscount();
					if (discount != 0) {
			%><td><%=product.getQuantity() * (product.getCost() - ((product.getCost() * discount) / 100))%></td>
			<%
				} else {
			%>
			<td><%=product.getQuantity() * product.getCost()%></td>
			<%
				}
			%>
			<td><%=product.getColour()%></td>
			<td><%=product.getQuantity()%></td>
			<td><img src="<%=product.getImageURL()%>" align="middle"></td>
			<td><%=product.getTimestamp()%></td>
		</tr>
		<%
			sum += product.getCost() * product.getQuantity();
		%>
		<tr>
			<td>total Amount:<%=sum%></td>
		</tr>
		<%
			}
		%>
	</table>

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
			action="orders?action=openorderpage&username=<%=name%>&limit=10&skip=<%=pagination.getSkip() + pagination.getLimit()%>&sort=<%=sortKey%>&sortorder=<%=sortOrder%>"
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
			action="orders?action=openorderpage&username=<%=name%>&limit=10&skip=<%=pagination.getSkip() - pagination.getLimit()%>&sort=<%=sortKey%>&sortorder=<%=sortOrder%>"
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
			action="orders?action=openorderpage&username=<%=name%>&limit=10&skip=<%=pagination.getSkip() - pagination.getLimit()%>&sort=<%=sortKey%>&sortorder=<%=sortOrder%>"
			method="POST">
			<button type="submit">
				<strong>Previous</strong>
			</button>
		</form>
		<br>
		<form
			action="orders?action=openorderpage&username=<%=name%>&limit=10&skip=<%=pagination.getSkip() + pagination.getLimit()%>&sort=<%=sortKey%>&sortorder=<%=sortOrder%>"
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