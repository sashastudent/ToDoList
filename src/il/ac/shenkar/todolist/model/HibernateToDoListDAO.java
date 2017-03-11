/**The Model, HibernateToDoListDAO class implement all methods in IToDoListDAO interface 
 * use for contact with data base mySQL 
 * user: hbuser password:hbuser */
package il.ac.shenkar.todolist.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class HibernateToDoListDAO implements IToDoListDAO {

	private static HibernateToDoListDAO hibernatToDoListDAO = null;
	private SessionFactory factory;
	
	/** 
	 * private constructor for implement singleton 
	 * */
	private HibernateToDoListDAO() {
		/* create one session factory for use in all sessions in program */
	    factory = new Configuration().configure().addAnnotatedClass(Item.class)
				.addAnnotatedClass(User.class).buildSessionFactory();
	}

	
	/**
	 * Implementation of singleton, return instance of HibernateToDoListDAO class
	 * @return hibernatToDoListDAO
	 */
	public static HibernateToDoListDAO getHibernateToDoListDAO() {

		if (hibernatToDoListDAO == null) {
			hibernatToDoListDAO = new HibernateToDoListDAO();

		}
		return hibernatToDoListDAO;
	}

	
	/**
	 *  create ToDo Item Task and insert to table Items in DB 
	 *  @param newTask new Item that user insert
	 * */
	@Override
	public void addTask(Item newTask) throws ToDoListException {

		Session session = factory.getCurrentSession();
		try {
			Item tempItem = new Item(newTask.getName(), newTask.getDescription(),newTask.getUserName());
			session.beginTransaction();
			session.save(tempItem);
			session.getTransaction().commit();
			System.out.println("Item Created!");

		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new ToDoListException(e.getMessage() + " ,Task not created!");
		} finally {

			session.close();
		}

	}

	/**
	 *  read Item Task from DB 
	 *  userName search item task with username 
	 *  @param userName
	 *  */
	@Override
	public Item readTask(String userName) throws ToDoListException {
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Item myTODOItem = session.get(Item.class, userName);
			session.getTransaction().commit();

			if (myTODOItem != null) {
				return myTODOItem;
			}

		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new ToDoListException(e.getMessage() + " ,can't read this Task from DB ");
		} finally {

			session.close();
		}

		throw new ToDoListException("Can't read This Task it Doesn't Exists !");

	}

	/**
	 *  add user to DB
	 *  @param  user the new user
	 *  */
	@Override
	public void addUser(User user) throws ToDoListException {
		Session session = factory.getCurrentSession();
		try {
			User tempUser = new User(user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword());
			session.beginTransaction();
			session.save(tempUser);
			session.getTransaction().commit();
			System.out.println("User created!");

		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new ToDoListException(e.getMessage() + " ,can't Add User");
		} finally {

			session.close();
		}
	}
	
	/**
	 *  get User from DB 
	 *  @param userName or email is unique name
	 *  */
	@Override
	public User getUser(String userName) throws ToDoListException {
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			User tempUser = session.get(User.class, userName);
			session.getTransaction().commit();
			System.out.println("Done!");

			if (tempUser != null) {
				return tempUser;
			}

		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new ToDoListException(e.getMessage() + " ,can't Read this User");

		} finally {

			session.close();
		}
		throw new ToDoListException("Can't Read This User, Doesn't Exists");
	}

	
	/**
	 *  get user ToDo list Task from BD with username
	 *  @param username is unique name in DB can be email also
	 * */
	@Override
	public List<Item> getToDoList(String username) throws ToDoListException {

		List<Item> todo = null;
		Query hql = null;
		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
			hql = session.createQuery("from Item where userName= :username").setParameter("username", username);

			List listResult = hql.getResultList();

			if (listResult != null) {
				return listResult;
			}

		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new ToDoListException(e.getMessage() + " ,can't Read this User and get todo list user");

		} finally {

			session.close();
		}
		throw new ToDoListException("Can't get item list this item list is null");

	}

	/**
	 *  delete specific item Task use id Task
	 *  @param id of task in DB
	 *  */
	@Override
	public void deleteTask(int id) throws ToDoListException {
		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
			Item tempItem = session.get(Item.class, id);
			if (tempItem != null) {
				session.delete(tempItem);
				session.getTransaction().commit();
				System.out.println("item deleted!");
				return;
			}

		} catch (HibernateException e) {

			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new ToDoListException(e.getMessage() + " ,can't delete This Task");

		} finally {

			session.close();
		}
		throw new ToDoListException("Can't delete This Task it Doesn't Exists !");

	}

	/** 
	 *  check if new username or email already exist in DB
	 *  @param newUserNamEmail the new email or username that user choose in registration
	 *  */
	@Override
	public boolean isUserNameEmailExists(String newUserNamEmail) throws ToDoListException {

		Query hql = null;
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			hql = session.createQuery("from User where userName= :newUserNamEmail").setParameter("newUserNamEmail",
					newUserNamEmail);

			List list = hql.getResultList();

			if (list.isEmpty()) {
				return false;
			}
		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new ToDoListException(e.getMessage() + " ,can't check if new username or email olrady exsissst ");

		} finally {

			session.close();
		}
		return true;
	}

	/**
	 *  update done value in To Do Item Task
	 *  @param id of task that need to update
	 *  */
	@Override
	public void updateTaskdone(int id) throws ToDoListException {
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Item myItem = session.get(Item.class, id);
			if (myItem != null) {
				if (myItem.getDone() != true) {
					myItem.setDone(true);
				} else {
					myItem.setDone(false);
				}
				session.getTransaction().commit();
				return;
			}

		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new ToDoListException(e.getMessage() + " ,can't update done value in This Task");

		} finally {

			session.close();
		}
		throw new ToDoListException("Can't Update done value This Task it Doesn't Exists !");

	}
	
	
	/**
	 *  update name and description of Task  
	 *  @param id of task that need to update
	 *  @param newToDoName new name Task
	 *  @param newToDescription new description task
	 *  */
	@Override
	public void updateTask(int id, String newToDoName, String newToDescription) throws ToDoListException{
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Item myItem = session.get(Item.class, id);
			if (myItem != null) {
				myItem.setName(newToDoName);
				myItem.setDescription(newToDescription);
				session.getTransaction().commit();
				System.out.println("ToDo item name updeted!");
				return;
			}

		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new ToDoListException(e.getMessage() + " ,can't update Task");

		} finally {

			session.close();
		}
		throw new ToDoListException("Can't Update This Task it Doesn't Exists !");
	}
}
