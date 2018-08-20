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
		boolean isRegistrationSuccess = true;
		if (request.getAttribute("isRegistrationSuccess") != null) {
			isRegistrationSuccess = (boolean) request.getAttribute("isRegistrationSuccess");
		}
		String errorMessage = (String) request.getAttribute("messege");
	%>
	<form action="user?action=register" method="POST">
		<table>

			<tr>
				<td>Name:</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input type="email" name="email"></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><input type="text" name="address"></td>
			</tr>
			<tr>
				<td>Customer Type:</td>
				<td><select name="usertype" required>
						<option value="choose">Choose</option>
						<option value="BUYER">BUYER</option>
						<option value="SELLER">SELLER</option>
				</select></td>
			</tr>
		</table>
		<%
			if (!isRegistrationSuccess) {
		%>
		<h5 style="color: red">This email is already registered.</h5>
		<%
			}
			if (errorMessage != null) {
		%>
		<h5 style="color: red">
			<%
				out.print(errorMessage);
				}
			%>
		</h5>
		<input type="submit" value="Register" name="register">
	</form>
	<br>
	<form action="login.jsp" method="POST">
		<input type="submit" value="Login" name="login">
	</form>
</body>
</html>