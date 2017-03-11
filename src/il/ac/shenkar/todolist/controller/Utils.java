/**
 * Utils class handle uses functions 
 */
package il.ac.shenkar.todolist.controller;

import il.ac.shenkar.todolist.model.ToDoListException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import il.ac.shenkar.todolist.model.IToDoListDAO;
import il.ac.shenkar.todolist.model.Item;
import il.ac.shenkar.todolist.model.User;

public class Utils {

	/**
	 * function check if the password match to the user in DB 
	 * @param name userName or email of user
	 * @param password that user insert and need to be checked
	 * @param factory for using DB
	 * @param hiber for using IToDListDAO interface 
	 */
	public boolean cheakPass(String name, String password, IToDoListDAO hiber) throws ToDoListException {

		try {

			User user = hiber.getUser(name);
			if (user != null) {
				String pass = user.getPassword();
				if (pass.equals(password)) {
					return true;
				}
			}

		} catch (ToDoListException e) {

			throw new ToDoListException(e + " ,can't read this user");
		}
		return false;
	}

	/**
	 * Create Cookie
	 * @param cookieName
	 * @param cookiValue
	 * @param cookiePath
	 * @param response
	 */
	public void createCookie(String cookieName, String cookiValue, String cookiePath, HttpServletResponse response) {

		Cookie cookie = new Cookie(cookieName, cookiValue);
		cookie.setMaxAge(24 * 60 * 60);
		cookie.setPath(cookiePath);
		response.addCookie(cookie);

	}
	
	/**
	 * Get Value of Cookie
	 * @param equalsString name of needed cookie 
	 * @param request
	 * @return cookieValue Value of Cookie
	 */
	public String getValueCookie(String equalsString, HttpServletRequest request) {

		String cookieValue = null;

		Cookie[] requestCookies = request.getCookies();
		if (requestCookies != null) {

			for (Cookie c : requestCookies) {
				if (c.getName().equals(equalsString)) {
					cookieValue = c.getValue();
				}
			}
		}

		return cookieValue;
	}

	/**
	 * Set Value Cookie To Null
	 * @param equalsString name of needed cookie 
	 * @param request
	 * @param response
	 */
	public void setValueCookieToNull(String equalsString, HttpServletRequest request, HttpServletResponse response) {

		Cookie[] requestCookies = request.getCookies();
		for (Cookie c : requestCookies) {
			if (c.getName().equals(equalsString)) {
				c.setValue(null);
				response.addCookie(c);

			}
		}
	}
	
	/**
	 * function did steps that required for login
	 * @param session
	 * @param userName
	 * @param logink if user wont to be keeping login 
	 * @param request
	 * @param response
	 * @param hiber for use DB function
	 * @param isAuthenticated
	 * @throws ToDoListException
	 */
	public void doLogin(HttpSession session, String userName, String logink, HttpServletRequest request, 
			HttpServletResponse response, IToDoListDAO hiber, boolean isAuthenticated ) throws ToDoListException{
				
		session.setAttribute("username", userName);				
		createCookie("username" , userName, "/", response);
		createCookie("loginkeep" , logink, "/", response);							
		User user = hiber.getUser(userName);
		// create cookies for first name and last name for display it in todolist
		createCookie("firstName" ,  user.getFirstName(), null, response);
		createCookie("lastName" ,  user.getLastName(), null, response);
		session.setAttribute("isAuthenticated", isAuthenticated);
		// get to do list of user and send it to todolist jsp file
		List<Item> todolist = hiber.getToDoList(userName);
		request.setAttribute("list", todolist);			
	}
	
	/**
	 * function did steps required for register new user
	 * @param session
	 * @param newUser
	 * @param hiber for use DB function
	 * @param response
	 * @param request
	 * @param isAuthenticated
	 * @throws ToDoListException
	 */
	public void doRegister(HttpSession session,User newUser, IToDoListDAO hiber, HttpServletResponse response, HttpServletRequest request, boolean isAuthenticated   ) throws ToDoListException{

		hiber.addUser(newUser);
		session.setAttribute("username", newUser.getUserName());
		// create cookies for first name and last name
		createCookie("firstName" ,  newUser.getFirstName(), "", response);
		createCookie("lastName" ,  newUser.getLastName(), "", response);
		session.setAttribute("isAuthenticated", isAuthenticated);
		List<Item> todolist = hiber.getToDoList(newUser.getUserName());
		request.setAttribute("list", todolist);
		
		
	}
}
