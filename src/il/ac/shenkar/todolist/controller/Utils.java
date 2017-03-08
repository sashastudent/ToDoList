package il.ac.shenkar.todolist.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import il.ac.shenkar.todolist.model.HiberException;
import il.ac.shenkar.todolist.model.IToDoListDAO;
import il.ac.shenkar.todolist.model.Item;
import il.ac.shenkar.todolist.model.User;

public class Utils {
	
	
public void addUser(String firstName, String lastName, String userName, String password, IToDoListDAO hiber){
	
// check if this userName already exists
	User user = null;
	try {
		  user = hiber.getUser( userName);
	} catch (HiberException e) {
		System.out.println(e.geteMessage());
		e.printStackTrace();
	}
		
	User Nuser = new User(firstName ,lastName, userName, password );
//if not go to model and create new user
	try {
		hiber.addUser(Nuser);
	} catch (HiberException e) {

		System.out.println(e.geteMessage());
		e.printStackTrace();
	} 
	
}


public void addItemToUser(String userName, String nameTodo, String descripToDo, int priorityToDo, IToDoListDAO hiber) {
//check if this user exists in DB
	User user = null;
	try {
		  user = hiber.getUser( userName);
	} catch (HiberException e) {
		System.out.println(e.geteMessage());
		e.printStackTrace();
	}
	
	Item newTask = new Item(nameTodo,descripToDo,priorityToDo,userName);
	
//if yes create item with this user name
	try {
		hiber.add_ToDo_Item(newTask);
	} catch (HiberException e) {

		System.out.println(e.geteMessage());
		e.printStackTrace();
	}
	
}

/**
 * function check if the password match to the user in DB
 *  @param name userName or email of user
 *  @param password that user insert and need to be checked
 *  @param factory for using DB
 *  @param hiber for using IToDListDAO interface
 * 
 * */
public boolean cheakPass(String name, String password, IToDoListDAO hiber)throws HiberException{

	
	try {
			
		User user = hiber.getUser(name);
		if(user!= null){
			String pass = user.getPassword();
			if(pass.equals(password))
			{
				return true;
			}
		}
						
	} catch (HiberException e) {
		
		throw new HiberException(e + " ,can't read this user");
	}
	return false;
}


	

}
