<!---------------ToDoList page view ---------------->
<%@ page language="java" contentType="text/html;  charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, il.ac.shenkar.todolist.model.* , il.ac.shenkar.todolist.controller.*" %>
<%@ page errorPage="todoErrorpage.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css" type="text/css" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body id="todolist">
	<form method="get" action="/ToDoListWebProject/todolist/mylist"
		autocomplete="off">
		<div id="myDIV" class="header">
			<a href="/ToDoListWebProject/todolist/mylist?action=logout"
				class="logout"><span class="glyphicon glyphicon-log-out"></span>Log-out</a>
			<h2 style="margin: 5px">
				My To Do List </br>Hello
				<%
				//use cookies- firstNameCookies and lastNameCookie that created in controller
				Utils utils = new Utils();
				String firstName =utils.getValueCookie("firstName", request);
				String lastName =utils.getValueCookie("lastName", request);
				if(firstName != null){out.write(firstName);}
				out.write(" ");
				if(lastName != null){out.write(lastName);}				
		    	%>
			</h2>

			<input type="text" id="myInput" name="taskname" placeholder="Title -">
			<input type="text" name="taskdescription" placeholder="Description...">
			<button type="submit"  class="addBtn"
				name="action" value="Add">Add</button>
		</div>
		<ul>
		<%
		    List<Item> usertodolist = (List)request.getAttribute("list");
			if (usertodolist != null) {
				if (usertodolist.isEmpty()) {
					out.print("<p> Your list empty add some task todo </p>");
				} else {
					for (Item task : usertodolist) {
						if (task.getDone() == true) {
							out.print("<li id='" + task.getItemId()
									+ "'class='checked'><a href='/ToDoListWebProject/todolist/mylist?action=did&id="
									+ task.getItemId() + "'>" +task.getItemId()+". "+task.getName() + "-&nbsp &nbsp" + task.getDescription()
									+ "</a><a href='/ToDoListWebProject/todolist/mylist?action=delete&id="
									+ task.getItemId() + "' class='close'>\u00D7</a></li>");
						} else if (task.getDone() == false) {
							out.print("<li id=" + task.getItemId()
									+ "><a href='/ToDoListWebProject/todolist/mylist?action=did&id=" + task.getItemId()
									+ "'>" +task.getItemId()+". "+ task.getName() + "-&nbsp &nbsp" + task.getDescription()
									+ "</a><a href='/ToDoListWebProject/todolist/mylist?action=delete&id="
									+ task.getItemId() + "' class='close'>\u00D7</a></li>");  
						}
					}

				}
			}
			%>
			</ul>	
 			<p class='edit'><input type='text' class='idtask' name='idedittask' placeholder='Id '/>
			<input type='text' class='title' name='edittaskname' placeholder='Edit Title'/>
			<input type='text' class='descr' name='editdescrip' placeholder='Edit Description'/>
			<button type='submit' class='addBtn' name='action' value='edit'>Edit</button> 
			</p>			
	</form>
</body>
</html>