<%@page import="org.online.shopping.entity.User "%>
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
		if (request.getAttribute(User.USERNAME) != null) {
			name = (String) request.getAttribute(User.USERNAME);
		}
		out.print("welcome " + name);
	%>
	<br>
	<br>
	<form action="Password.jsp?name=<%=name%>" method="POST">
		<button>Change Password</button>
	</form>
	<br>
	<br>
	<form
		action="products?action=displaybuyerproducts&name=<%=name%>&limit=10&skip=0&sort=timestamp&sortorder=desc"
		method="POST">
		<input type="submit" value="ProductDetails">
	</form>
</body>
</html>