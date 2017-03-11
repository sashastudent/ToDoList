<!----------- Login and register user form ------------->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page errorPage="todoErrorpage.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="UTF-8" />
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
        <title>ToDo List login </title>
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
		<link href="https://fonts.googleapis.com/css?family=Raleway:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css" />
		<script src="${pageContext.request.contextPath}/scripts.js"  type="text/js"></script>
    </head>
    <body>
 <!----------------------------------------------------------------- Login form ---------------------------------------------------------->
 
 <%
   
// if user did "keep me login" he redirect to todolist skiping login
	Cookie[] requestCookies = request.getCookies();
	if (requestCookies != null) {
		for (Cookie c : requestCookies) {
			if (c.getName().equals("loginkeep")) {
				
				if(c.getValue().equalsIgnoreCase("loginkeeping")){
					session.setAttribute("isAuthenticated","true");
					response.sendRedirect("/ToDoListWebProject/todolist/login");
				}
			
			}
			
		}

	}
 
 %>
        <div class="container">           
            <header>
                <h1>ToDo List Login and Registration Form</h1>			
            </header>
            <section>				
                <div id="container_demo" >
                    <!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <form method="post" action="/ToDoListWebProject/todolist/login" autocomplete="on" > 
                                <h1>Log in</h1> 
                                <p> 
                                    <label for="username" class="uname" > Your email or username </label>
                                    <input id="username" name="username" required="required" type="text" placeholder="myusername or mymail@mail.com" value="${userName}"/>
                                </p>
                                <p class="errorP">
                                		<% 
											String badUsername = (String)request.getAttribute("isBaduserName");
										
											if("badUsername".equalsIgnoreCase(badUsername))
											{
												out.print("The user does not exist.");
											}															
										%>
                                                              
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd"> Your password </label>
                                    <input id="password" name="password" required="required" type="password" placeholder="eg. X8df!90EO" /> 
                                </p>
                                <P class="errorP">
                                		<% 
											String badpass = (String)request.getAttribute("isBadPassword");
										
											if("badPassword".equalsIgnoreCase(badpass))
											{
												out.print("The passwords do not match!");
											}															
										%>
                                </P>
                                <p class="keeplogin"> 
									<input type="checkbox" name="loginkeeping" id="loginkeeping" value="loginkeeping" /> 
									<label for="loginkeeping">Keep me logged in</label>
								</p>
                                <p class="login button"> 
                                    <input type="submit" value="Login" /> 
								</p>
                                <p class="change_link">
									Not a member yet ?
									<a href="#toregister" class="to_register">Join us</a>
								</p>
                            </form>
                        </div>                  
<!----------------------------------------------------------------- Register form ---------------------------------------------------------->
                        <div id="register" class="animate form">
                            <form  method="post" action="/ToDoListWebProject/todolist/register" autocomplete="on"> 
                                <h1> Sign up </h1> 
                                 <p> 
                                    <label for="usernamesignup" class="uname" >Your first Name</label>
                                    <input id="usernamesignup" name="firstname" required="required" type="text" placeholder="myFirstname"  value="${firstName}"/>
                                </p>
                                <p> 
                                    <label for="usernamesignup" class="uname" >Your last Name</label>
                                    <input id="usernamesignup" name="lastname" required="required" type="text" placeholder="myLastname" value="${lastName}"/>
                                </p>
                                <p> 
                                    <label for="emailsignup" class="youmail"  > Your email or user name</label>
                                    <input id="emailsignup" name="emailusername" required="required" type="text" placeholder="mysupermail@mail.com/myusername"/> 
                                </p>
                                       <p class="errorP">
                                		<% 
											String ExistedUser = (String)request.getAttribute("isExistedBaduserName");
										
											if("exsitedUserName".equalsIgnoreCase(ExistedUser))
											{
												out.print("A user with this email or user name alrady exists.");
											}															
										%>
                                                              
                                </p>
                                <p> 
                                    <label for="passwordsignup" class="youpasswd" >Your password </label>
                                    <input id="passwordsignup" name="passwordsignup" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                               
                                <p class="signin button"> 
									<input type="submit" value="Sign up"/> 
								</p>
                                <p class="change_link">  
									Already a member ?
									<a href="#tologin" class="to_register"> Go and log in </a>
								</p>
                            </form>
                        </div>
						
                    </div>
                </div>  
            </section>
        </div>
          <jsp:include page="copyright.jsp" />
    </body>
</html>