<%@page import="java.util.Map"%>
<%@page import="org.online.shopping.entity.Cart"%>
<%@page import="org.online.shopping.entity.User"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
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
	<div align="right">
		<h3 align="center">
			Welcome
			<%=request.getAttribute("name")%></h3>
		<form
			action="products?action=displaybuyerproducts&name=<%=request.getAttribute("name")%>&limit=10&skip=0&sort=timestamp&sortorder=desc"
			method="POST">
			<button type="submit">Product Details</button>
		</form>
	</div>
	<table align="center" border="1">
		<tr>
			<th>NAME</th>
			<th>COLOUR</th>
			<th>IMAGE</th>
			<th>Quantity</th>
			<th>TotalCost</th>
		</tr>
		<%
			String errorMessage = (String) request.getAttribute("errormessage");
			double sum = 0;
			@SuppressWarnings("unchecked")
			Collection<Product> products = (Collection<Product>) request.getAttribute(User.PRODUCTS);
			Iterator<Product> iterator = products.iterator();
			while (iterator.hasNext()) {
				Product product = iterator.next();
		%>
		<tr>
			<td><%=product.getName()%></td>
			<td><%=product.getColour()%></td>
			<td><img src="<%=product.getImageURL()%>"></td>
			<td>
				<%
					if (String.valueOf(request.getAttribute(Product.MESSAGE)) != null
								&& product.getId() == request.getAttribute(Product.PRODUCT_ID)) {
				%>
				<h4 style="color: red"><%=request.getAttribute(Product.MESSAGE)%></h4>
				<%
					}
				%>
				<form
					action="cart?action=updatecart&name=<%=request.getAttribute("name")%>"
					method="POST">
					<select name="values">
						<option value="0">0</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="<%=product.getQuantity()%>" selected><%=product.getQuantity()%></option>
					</select> <input type="submit" name="<%=product.getId()%>"
						value="UpdateCart">
				</form>
			</td>
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
		</tr>
		<%
			sum += product.getQuantity() * product.getCost();
			}
		%>
	</table>
	<br>
	<div align="center">
		<%
			if (request.getAttribute(Cart.AMOUNT) != null && errorMessage == null) {
				sum = Double.valueOf(request.getAttribute(Cart.AMOUNT).toString());
				double discount = Double.valueOf(request.getAttribute(Cart.DISCOUNT).toString());
		%>
		Discount Amount:
		<%=discount%>
		<%
			}
		%>
	</div>
	<div align="center">
		<h3 align="center">
			Amount To Pay:
			<%=sum%>
		</h3>
		<form
			action="cart?action=pay&name=<%=request.getAttribute("name")%>&amount=<%=sum%>&limit=10&skip=0&sort=timestamp&sortorder=desc"
			method="POST">
			<button type="submit">Pay</button>
		</form>
	</div>
	<br>
	<div align="center">
		<form
			action="coupon?action=apply&name=<%=request.getAttribute("name")%>&amount=<%=sum%>"
			method="POST">
			<input type="text" name="coupon">
			<button type="submit">Apply Coupon</button>
		</form>
		<%
			if (errorMessage != null) {
		%>
		<h5 style="color: red">
			<%
				out.print(errorMessage);
				}
			%>
		</h5>
	</div>
</body>
</html>