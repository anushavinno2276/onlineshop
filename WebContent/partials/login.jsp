<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Online Shopping</title>
</head>
<body>
	<%
		boolean isLoginSuccess = true;
		if (request.getAttribute("isLoginSuccess") != null) {
			isLoginSuccess = (boolean) request.getAttribute("isLoginSuccess");
		}
		boolean isActive = true;
		if (request.getAttribute("isActive") != null) {
			isActive = (boolean) request.getAttribute("isActive");
		}
		if (!isActive) {
	%>
	<h5 style="color: green">Please Activate your Account</h5>
	<%
		}
	%>
	<form
		action="login?action=login&limit=10&skip=0&sort=timestamp&sortorder=desc"
		method="POST">
		<table>
			<tr>
				<td>Username :</td>
				<td><input type="text" name="username" required /></td>
			</tr>
			<tr>
				<td>Password :</td>
				<td><input type="password" name="password" required /></td>
			</tr>
		</table>
		<%
			if (!isLoginSuccess) {
		%>
		<h6 style="color: red">Wrong username/password</h6>
		<%
			}
		%>
		<input type="submit" value="Login" />
	</form>
	<br>
	<form action="Register.jsp" method=POST>
		<input type="submit" value="Register">
	</form>
</body>
</html>