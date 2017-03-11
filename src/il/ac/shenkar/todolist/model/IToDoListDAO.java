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
	 *  create ToDo Item Task and insert to table Items in DB 
	 *  @param newTask new Item that user insert
	 * */
	public void addTask(Item newTask)
			throws ToDoListException;
	
	/**
	 *  get user ToDo list Task from BD with username
	 *  @param username is unique name in DB can be email also
	 * */
	public List<Item> getToDoList(String username) throws ToDoListException; 
	
	/**
	 *  read Item Task from DB 
	 *  userName search item task with username 
	 *  @param userName
	 *  */
	public Item readTask(String userName) throws ToDoListException;


	/**
	 *  add user to DB
	 *  @param  user the new user
	 *  */
	public void addUser(User user)throws ToDoListException;
		
	/**
	 *  get User from DB 
	 *  @param userName or email is unique name
	 *  */
	public User getUser(String userName) throws ToDoListException;

	/**
	 *  delete specific item Task use id Task
	 *  @param id of task in DB
	 *  */
	public void deleteTask(int id) throws ToDoListException;
	
	
	/** 
	 *  check if new username or email already exist in DB
	 *  @param newUserNamEmail the new email or username that user choose in registration 
	 *  */
	public boolean isUserNameEmailExists(String newUserNamEmail)throws  ToDoListException;
	

	/**
	 *  update done value in To Do Item Task
	 *  @param id of task that need to update
	 *  */
	public void updateTaskdone(int id) throws ToDoListException;
		
	
	/**
	 *  update name and description of Task  
	 *  @param id of task that need to update
	 *  @param newToDoName new name Task
	 *  @param newToDescription new description task
	 *  */
	public void updateTask(int id, String newToDoName, String newToDescription) throws ToDoListException;

	
}
