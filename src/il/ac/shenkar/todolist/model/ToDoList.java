package il.ac.shenkar.todolist.model;

import java.util.LinkedList;
import java.util.*;

public class ToDoList {

	 LinkedList<Item> todolist = new LinkedList<Item>();
	//private String userName;
	 
	
	 
	public void addTask(Item taskToDo){
		if(taskToDo!=null)
		{
			todolist.push(taskToDo);
		}		
	}
	
	public void addList(List list){
		if(list !=null){
			todolist.addAll(list);
		}
	}
	
	public LinkedList<Item> getTodolist()
	{
		if(todolist==null)
		{
			todolist = new LinkedList<Item>();
		}
		return todolist;
		
	}
	
	public boolean isEmpty(){
		
		if(todolist.isEmpty())
		{
			return true;
			
		}		
		return false;	
	}
	
	/*public String print(){
		
		StringBuffer sb = new StringBuffer();
		for(Item task : this.todolist ){
			sb.append("<li id="+task.getItemId()+">"+task.getName()+" -&nbsp &nbsp  "+task.getDescription()+"</li>");
			<jsp:useBean scope="page" id="task.getItemId()" class="java.util.Date" />
			
		}
		return sb.toString();
	}*/
	
	
	
}
