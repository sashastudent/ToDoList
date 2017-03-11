/**
 * Servlet implementation class ToDoListController
 * The Controller handles all requests that come from view.
 * Handle on add delete update Tasks, add new user, 
 * do login and more..
 */
package il.ac.shenkar.todolist.controller;

import il.ac.shenkar.todolist.model.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/todolist/*")
public class ToDoListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ToDoListController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Utils utils = new Utils(); // for using utils functions
		response.setContentType("text/html");
		IToDoListDAO hiber = HibernateToDoListDAO.getHibernateToDoListDAO();
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();
		String path = request.getPathInfo();
		boolean isAuthenticated = false; //for indicate Authenticated of user

		// switch case for check user patch
		switch (path) {
		// case if user did login
		case "/login":

			String userName = request.getParameter("username");
			String userPass = request.getParameter("password");
			String logink = request.getParameter("loginkeeping");
			
			try {
				//if user wont to be keep login //////////
				if (session.getAttribute("isAuthenticated") == "true") {
					isAuthenticated = true;
				}
				if (isAuthenticated && userName == null) {
					userName= utils.getValueCookie("username", request);
				}
				//////////////////////////////////////////
				if (userName == null || userName.isEmpty()) {
					response.sendRedirect("/ToDoListWebProject/index.jsp");
					return;
				}
				if (!hiber.isUserNameEmailExists(userName)) {
					// username do not exist in DB send in request attribute that indicate bad	insert username																																								
					request.setAttribute("isBaduserName", "badUsername");
					getServletContext().getRequestDispatcher("/index.jsp").include(request, response);
					return;
				}
				if (!isAuthenticated) {
					if (!utils.cheakPass(userName, userPass, hiber)) {
						// password do not match send in request attribute that indicate bad password
						String bedUsername = "badPassword";
						request.setAttribute("userName", userName);
						request.setAttribute("isBadPassword", bedUsername);
						getServletContext().getRequestDispatcher("/index.jsp").include(request, response);
						return;
					}
				}
				// if password match with password in DB//	
				// do authentication
				isAuthenticated = true;
				utils.doLogin(session, userName,logink, request, response, hiber, isAuthenticated );
		    	getServletContext().getRequestDispatcher("/todolist.jsp").include(request, response);
				return;
				
			} catch (ToDoListException e) {

				request.setAttribute("errorMessage", e);
				getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
				
			} catch (Exception e) {
				ToDoListException ex = new ToDoListException(e.getMessage());
				request.setAttribute("errorMessage", ex);
				getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
			}

			break;
		// case if user did registration
		case "/register":
			String firstName = request.getParameter("firstname");
			String lastName = request.getParameter("lastname");
			String userNamemail = request.getParameter("emailusername");
			String password = request.getParameter("passwordsignup");

			// check if this user do not exist in DB already
			try {
				if (userNamemail != null && !userNamemail.isEmpty()) {
					if (hiber.isUserNameEmailExists(userNamemail)) {
						request.setAttribute("isExistedBaduserName", "exsitedUserName");
						request.setAttribute("firstName", firstName);
						request.setAttribute("lastName", lastName);
						getServletContext().getRequestDispatcher("/index.jsp").include(request, response);
						return;
					} else {
						// user not exist create new user
						User newUser = new User(firstName, lastName, userNamemail, password);
						// do authentication
						isAuthenticated = true;
						utils.doRegister(session, newUser, hiber,  response, request, isAuthenticated );
						getServletContext().getRequestDispatcher("/todolist.jsp").include(request, response);
						return;
					}
				} else {
					response.sendRedirect("/ToDoListWebProject/index.jsp");
					return;
				}
			} catch (ToDoListException e) {
				request.setAttribute("errorMessage", e);
				getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
				
			}catch (Exception e) {   
				ToDoListException ex = new ToDoListException(e.getMessage());
				request.setAttribute("errorMessage", ex);
				getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
				}

			session.setAttribute("username", request.getParameter("username"));
			dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.include(request, response);
			break;
		// case if user use ToDo List and did some action
		case "/mylist":
			/** Check Is Authenticated **/
			if ((boolean) session.getAttribute("isAuthenticated") != true) {
				// user do logout, do include to login
				response.sendRedirect("/ToDoListWebProject/index.jsp");
				return;
			}
			/** End Is Authenticated **/
			String action = request.getParameter("action");
			// take user name of user from session for add task to specific user
			userName = (String) session.getAttribute("username");

			// start check the action that user did
			// user add new task to ToDo list
			if ("Add".equalsIgnoreCase(action)) {
				String taskName = request.getParameter("taskname");
				String taskDescription = request.getParameter("taskdescription");
				if (taskName != null && taskDescription != null) {
					taskName = taskName.trim();
					taskDescription = taskDescription.trim();
					Item newTask = new Item(taskName, taskDescription, userName);
					try {
						hiber.addTask(newTask);
						List<Item> todolist = hiber.getToDoList(userName);
						request.setAttribute("list", todolist);
						getServletContext().getRequestDispatcher("/todolist.jsp").include(request, response);
						return;
						
					} catch (ToDoListException e) {
						request.setAttribute("errorMessage", e);
						getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
						
					}catch (Exception e) {
						ToDoListException ex = new ToDoListException(e.getMessage());
						request.setAttribute("errorMessage", ex);
						getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
						}

				}
			// user delete some task from ToDo list
			} else if ("delete".equalsIgnoreCase(action)) {

				int taskid = Integer.parseInt(request.getParameter("id"));

				try {
					hiber.deleteTask(taskid);
					List<Item> todolist = hiber.getToDoList(userName);
					request.setAttribute("list", todolist);
					getServletContext().getRequestDispatcher("/todolist.jsp").include(request, response);
					return;
					
				} catch (ToDoListException e) {
					request.setAttribute("errorMessage", e);
					getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
					
				}catch (Exception e) {	    
					ToDoListException ex = new ToDoListException(e.getMessage());
					request.setAttribute("errorMessage", ex);
					getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
					}
				
			// user mark some task to done
			} else if ("did".equalsIgnoreCase(action)) {

				int taskid = Integer.parseInt(request.getParameter("id"));
				try {
					hiber.updateTaskdone(taskid);
					List<Item> todolist = hiber.getToDoList(userName);
					request.setAttribute("list", todolist);
					getServletContext().getRequestDispatcher("/todolist.jsp").include(request, response);
					return;
					
				} catch (ToDoListException e) {
					request.setAttribute("errorMessage", e);
					getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
					
				}catch (Exception e) {
					ToDoListException ex = new ToDoListException(e.getMessage());
					request.setAttribute("errorMessage", ex);
					getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
					}
			//user edit some task
			} else if ("edit".equalsIgnoreCase(action)) {

				if (request.getParameter("idedittask") != null) {
					try {
						int taskId = Integer.parseInt(request.getParameter("idedittask"));
						String taskName = request.getParameter("edittaskname");
						String taskDescription = request.getParameter("editdescrip");
						if (taskName != null && taskDescription != null) {
							taskName = taskName.trim();
							taskDescription = taskDescription.trim();
							hiber.updateTask(taskId, taskName, taskDescription);
							List<Item> todolist = hiber.getToDoList(userName);
							request.setAttribute("list", todolist);
							getServletContext().getRequestDispatcher("/todolist.jsp").include(request, response);
							return;
						}
					} catch (NumberFormatException ex) {
						ToDoListException e = new ToDoListException("Invalid input: " + ex.getMessage());
						request.setAttribute("errorMessage", e);
						getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
						
					} catch (ToDoListException e) {
						request.setAttribute("errorMessage", e);
						getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
						
					}catch (Exception e) {
						ToDoListException ex = new ToDoListException(e.getMessage());
						request.setAttribute("errorMessage", ex);
						getServletContext().getRequestDispatcher("/todoErrorpage.jsp").include(request, response);
						}
				}
			// user did logout
			} else if ("logout".equalsIgnoreCase(action)) {
				
			    utils.setValueCookieToNull("loginkeep", request, response);		
			    utils.setValueCookieToNull("username", request, response);	
				session.invalidate();
				isAuthenticated = false;
				response.sendRedirect("/ToDoListWebProject/index.jsp");
				return;
			
			}
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
