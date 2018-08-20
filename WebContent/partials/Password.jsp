<%@page import="org.online.shopping.entity.User"%>
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
		String name = request.getParameter(User.NAME);
		out.print("welcome" + name);
		String errorMessage = (String) request.getAttribute("message");
	%>
	<br>
	<br>
	<form action="user?action=updatepassword&name=<%=name%>" method="post">
		<table>
			<tr>
				<td>Old Password:</td>
				<td><input type="password" name="oldpassword" required></td>
			</tr>
			<tr>
				<td>New Password:</td>
				<td><input type="password" name="newpassword" required></td>
			</tr>
			<tr>
				<td>Confirm Password:</td>
				<td><input type="password" name="confirmpassword" required></td>
			</tr>
			<tr>
				<td><input type="submit" value="Change Password" align="middle"></td>
			</tr>
		</table>
		<%
			if (errorMessage != null) {
		%>
		<h5 style="color: red"></h5>
		<%
			out.print(errorMessage);
			}
		%>

	</form>
</body>
</html>