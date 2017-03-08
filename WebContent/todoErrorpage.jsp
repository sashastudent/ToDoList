<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*, il.ac.shenkar.todolist.model.HiberException"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ToDO list Exceptoin</title>
</head>
<body>
	<h2>An Exception has occurred!</h2>
	<hr>
	<table>
		<tr>
			<td>Exception Class: </td>
			<td>
				<%
					HiberException error = (HiberException) request.getAttribute("errorMessage");
					out.print(error.getClass());
				%>
			</td>
		</tr>
		<tr>
			<td>Message:</td>
			<td><%out.print(error.geteMessage());%></td>
		</tr>		
	</table>
	<hr>
	<br>
</body>
</html>
