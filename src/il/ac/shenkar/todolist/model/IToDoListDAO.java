/** The Model Interface, hold lists the methods through witch 
 * the web application uses database */

package il.ac.shenkar.todolist.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface IToDoListDAO {

	/** 
	 *  create one session factory for use in all sessions in program
	 * */
//	public SessionFactory gettingSassuionFactory();

	/**
	 *  create Item and insert to table Items in DB 
	 *  @param
	 *  @param
	 * */
	public void add_ToDo_Item(Item newTask)
			throws HiberException;
	
	/**
	 *  get user ToDo list 
	 *  @param
	 *  @param
	 * */
	public List<Item> getToDoList(String username) throws HiberException; 
	
	/**
	 *  read Item from DB 
	 *  @param
	 *  @param
	 *  */
	public Item read_ToDo_Item(String userName) throws HiberException;


	/**
	 * add user
	 *  @param
	 *  @param
	 *  */
	public void addUser(User user)throws HiberException;
		
	/**
	 *  get User from DB 
	 *  @param
	 *  @param
	 *  */
	public User getUser(String userName) throws HiberException;

	/**
	 *  delete specific To Do item use id 
	 *  @param
	 *  @param
	 *  */
	public void deleteToDo_Item(int id) throws HiberException;
	
	
	/** 
	 *  check if new username or email already exist in DB
	 *  @param newUserNamEmail the new email or username that user choose in registration
	 *  @param factory for using DB 
	 *  */
	public boolean isUserNameEmailExists(String newUserNamEmail)throws  HiberException;
	

	/**
	 *  update done value in To Do Item 
	 *  @param id of task that need to update
	 *  @param factory for using DB
	 *  */
	public void updateItemTo_Do_done(int id) throws HiberException;
		
	
	/**
	 *  update name and description of Task  
	 *  @param id of task that need to update
	 *  @param newToDoName new name Task
	 *  @param newToDescription new description task
	 *  @param factory for using DB
	 *  */
	public void updateItemTo_Do_Item(int id, String newToDoName, String newToDescription) throws HiberException;

	
}
