/**
 * Mini stand alone application 
 * Demo class for using DB 
 * user name & password in mysql : hbuser 
 * only for tasting of IToDoListDAO functions
 * */

package il.ac.shenkar.todolist.model;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ToDoListDemo {

	public static void main(String[] args) {

		IToDoListDAO hiber = HibernateToDoListDAO.getHibernateToDoListDAO();

		User user = new User("vanilaa", "Blank", "vanilaa", "12sa");

		try {
			//adding new user to DB
			hiber.addUser(user);
			//add to user new task
			Item taskBuy = new Item("buy", "buy colla", user.getUserName());
			//read user
			User user2 = hiber.getUser(user.getUserName());
			//add task 
			hiber.addTask(taskBuy);
			//get todo list of user
			List<Item> list = hiber.getToDoList(user.getUserName());
			for (Item temp : list) {
				System.out.println("Content: " + temp);
			}
		} catch (ToDoListException e) {

			System.out.println(e.geteMessage());
			e.printStackTrace();
		}


	}

}
