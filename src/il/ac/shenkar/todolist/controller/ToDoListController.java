package il.ac.shenkar.todolist.controller;

import il.ac.shenkar.todolist.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Servlet implementation class ToDoListController
 */
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

		Utils utils = new Utils();
		response.setContentType("text/html, text/css");
		IToDoListDAO hiber = HibernateToDoListDAO.getHibernateToDoListDAO();
		//SessionFactory factory = hiber.gettingSassuionFactory();

		// check path and do action
		String path = request.getPathInfo();
		RequestDispatcher dispatcher = null;
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		boolean isAuthenticated = false;
		Cookie firstNameCookie = null;
		Cookie lastNameCookie = null;
		// writer.flush();

		switch (path) {
		// case if user come from login page
		case "/login":
			String userName = request.getParameter("username");
			String userPass = request.getParameter("password");

			try {
				//check if this user name or email exist in DB and if he exist check if inserted password match 
				if (hiber.isUserNameEmailExists(userName)) {
					if (utils.cheakPass(userName, userPass, hiber)) {
						// if password match with password in DB
						session.setAttribute("username", userName);
						isAuthenticated = true;
						// session.setAttribute("isAuthenticated",
						// isAuthenticated);
						// request.setAttribute("username", userName);
						User user = hiber.getUser(userName);
						// create cookies for first name and last name
						firstNameCookie = new Cookie("firstName", user.getFirstName());
						lastNameCookie = new Cookie("lastName", user.getLastName());
						firstNameCookie.setMaxAge(24 * 60 * 60);
						lastNameCookie.setMaxAge(24 * 60 * 60);
						response.addCookie(firstNameCookie);
						response.addCookie(lastNameCookie);
						List<Item> todolist = hiber.getToDoList(userName);
						request.setAttribute("list", todolist);
						getServletContext().getRequestDispatcher("/todolist.jsp").forward(request, response);
						return;
					} else {
						// password do not match
						// send in request attribute that indicate bad password
						String bedUsername = "badPassword";
						request.setAttribute("userName", userName);
						request.setAttribute("isBadPassword", bedUsername);
						getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
						return;

					}
				}else{
					// username do not exist in DB
					// send in request attribute that indicate bad insert username
					String badUserName = "badUsername";
					request.setAttribute("isBaduserName", badUserName);
					getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
					return;
				}

			} catch (HiberException e) {

				///System.out.println(e1.geteMessage());
				//e1.printStackTrace();
				request.setAttribute("errorMessage", e);
				getServletContext().getRequestDispatcher("/todoErrorpage.jsp").forward(request, response);

			}
			break;
		// case if user come from register page
		case "/register":
			String firstName = request.getParameter("firstname");
			String lastName = request.getParameter("lastname");
			String userNamemail = request.getParameter("emailusername");
			String password = request.getParameter("passwordsignup");

			// check if this user do not exist in DB already
			try {
				if (hiber.isUserNameEmailExists(userNamemail)) {
					request.setAttribute("isExistedBaduserName", "exsitedUserName");
					request.setAttribute("firstName", firstName);
					request.setAttribute("lastName", lastName);
					getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
					return;
				} else {
					// user not exist create new user
					User newUser = new User(firstName, lastName, userNamemail, password);
					hiber.addUser(newUser);
					session.setAttribute("username", userNamemail);
					isAuthenticated = true;
					session.setAttribute("isAuthenticated", isAuthenticated);
					firstNameCookie = new Cookie("firstName", firstName);
					lastNameCookie = new Cookie("lastName", lastName);
					firstNameCookie.setMaxAge(24 * 60 * 60);
					lastNameCookie.setMaxAge(24 * 60 * 60);
					response.addCookie(firstNameCookie);
					response.addCookie(lastNameCookie);
					List<Item> todolist = hiber.getToDoList(userNamemail);
					request.setAttribute("list", todolist);
					getServletContext().getRequestDispatcher("/todolist.jsp").forward(request, response);
					return;
				}
			} catch (HiberException e) {
				request.setAttribute("errorMessage", e);
				getServletContext().getRequestDispatcher("/todoErrorpage.jsp").forward(request, response);
			}

			session.setAttribute("username", request.getParameter("username"));
			dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			break;
		// case if user come from myToDolist page
		case "/mylist":

			/** Check Is Authenticated **/
			// if ((boolean) session.getAttribute("isAuthenticated") != true) {
			// user do logout, do forward to login
			// response.sendRedirect("/ToDoListWebProject/index.jsp");
			// return;
			// }
			/** End Is Authenticated **/

			String action = request.getParameter("action");
			// take user name of user from session for add task to specific user
			userName = (String) session.getAttribute("username");
			
			//start check the action 
			if ("Add".equalsIgnoreCase(action)) {
				String taskName = request.getParameter("taskname");
				String taskDescription = request.getParameter("taskdescription");
				if (taskName != null && taskDescription != null) {
					taskName = taskName.trim();
					taskDescription = taskDescription.trim();
					Item newTask = new Item(taskName, taskDescription, 0, userName);
					try {
						hiber.add_ToDo_Item(newTask);
						List<Item> todolist = hiber.getToDoList(userName);
						request.setAttribute("list", todolist);
						getServletContext().getRequestDispatcher("/todolist.jsp").forward(request, response);
						return;
					} catch (HiberException e) {
						request.setAttribute("errorMessage", e);
						getServletContext().getRequestDispatcher("/todoErrorpage.jsp").forward(request, response);
					}

				}

			} else if ("delete".equalsIgnoreCase(action)) {

				int taskid = Integer.parseInt(request.getParameter("id"));

				try {
					hiber.deleteToDo_Item(taskid);
					List<Item> todolist = hiber.getToDoList(userName);
					request.setAttribute("list", todolist);
					getServletContext().getRequestDispatcher("/todolist.jsp").forward(request, response);
					return;
				} catch (HiberException e) {
					request.setAttribute("errorMessage", e);
					getServletContext().getRequestDispatcher("/todoErrorpage.jsp").forward(request, response);
				}

			} else if ("did".equalsIgnoreCase(action)) {

				int taskid = Integer.parseInt(request.getParameter("id"));
				try {
					hiber.updateItemTo_Do_done(taskid);
					List<Item> todolist = hiber.getToDoList(userName);
					request.setAttribute("list", todolist);
					getServletContext().getRequestDispatcher("/todolist.jsp").forward(request, response);
					return;
				} catch (HiberException e) {
					request.setAttribute("errorMessage", e);
					getServletContext().getRequestDispatcher("/todoErrorpage.jsp").forward(request, response);
				}
			} else if ("edit".equalsIgnoreCase(action)) {

				if (request.getParameter("idedittask") != null) {
					try {
						int taskId = Integer.parseInt(request.getParameter("idedittask"));
						String taskName = request.getParameter("edittaskname");
						String taskDescription = request.getParameter("editdescrip");
						if (taskName != null && taskDescription != null) {
							taskName = taskName.trim();
							taskDescription = taskDescription.trim();
							hiber.updateItemTo_Do_Item(taskId,taskName, taskDescription);
							List<Item> todolist = hiber.getToDoList(userName);
							request.setAttribute("list", todolist);
							getServletContext().getRequestDispatcher("/todolist.jsp").forward(request, response);
							return;
						}
					} catch (NumberFormatException ex) {
						System.out.print("invalid id ");
						request.setAttribute("errorMessage", ex);
						getServletContext().getRequestDispatcher("/todoErrorpage.jsp").forward(request, response);
					} catch (HiberException e) {
						request.setAttribute("errorMessage", e);
						getServletContext().getRequestDispatcher("/todoErrorpage.jsp").forward(request, response);
					}
				}

			} else if ("logout".equalsIgnoreCase(action)) {
				session.invalidate();
				isAuthenticated = false;
				// firstNameCookie=0;
				// lastNameCookie=0;
				// response.addCookie(firstNameCookie);
				// response.addCookie(lastNameCookie);
				// getServletContext().getRequestDispatcher("/todolist.jsp").forward(request,
				// response);
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
