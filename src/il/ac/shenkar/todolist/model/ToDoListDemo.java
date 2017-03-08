/**Demo class for using DB 
 * user name & password in mysql : hbuser */

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
			hiber.addUser(user);
			Item taskBuy = new Item("buy", "buy colla", 1, user.getUserName());
			User user2 = hiber.getUser(user.getUserName());
			hiber.add_ToDo_Item(taskBuy);
			List<Item> list = hiber.getToDoList(user.getUserName());
			for (Item temp : list) {
				System.out.println("Content: " + temp);
			}
		} catch (HiberException e) {

			System.out.println(e.geteMessage());
			e.printStackTrace();
		}


	}

}
