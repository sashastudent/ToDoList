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
	 * private contractor for implement singleton 
	 * */
	private HibernateToDoListDAO() {
		/* create one session factory for use in all sessions in program */
	    factory = new Configuration().configure().addAnnotatedClass(Item.class)
				.addAnnotatedClass(User.class).buildSessionFactory();
	}

	public static HibernateToDoListDAO getHibernateToDoListDAO() {

		if (hibernatToDoListDAO == null) {
			hibernatToDoListDAO = new HibernateToDoListDAO();

		}
		return hibernatToDoListDAO;
	}

	/* create one session factory for use in all sessions in program */
/*	@Override
	public SessionFactory gettingSassuionFactory() {

		// create session factory for getting session
		SessionFactory factory = new Configuration().configure().addAnnotatedClass(Item.class)
				.addAnnotatedClass(User.class).buildSessionFactory();

		return factory;
	}*/

	/* The method create the item and insert it in to Table of Items in DB */
	@Override
	public void add_ToDo_Item(Item newTask) throws HiberException {

		Session session = factory.getCurrentSession();
		try {
			Item tempItem = new Item(newTask.getName(), newTask.getDescription(), newTask.getPriority(),
					newTask.getUserName());
			session.beginTransaction();
			session.save(tempItem);
			session.getTransaction().commit();
			System.out.println("Item Created!");

		} catch (Exception e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new HiberException(e.getMessage() + " ,Task not created!");
		} finally {

			session.close();
		}

	}

	/* read Item from DB */
	@Override
	public Item read_ToDo_Item(String userName) throws HiberException {
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Item myTODOItem = session.get(Item.class, userName);
			session.getTransaction().commit();

			if (myTODOItem != null) {
				return myTODOItem;
			}

		} catch (Exception e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new HiberException(e.getMessage() + " ,can't read this Task from DB ");
		} finally {

			session.close();
		}

		throw new HiberException("Can't read This Task it Doesn't Exists !");

	}

	/**
	 * add user
	 */
	@Override
	public void addUser(User user) throws HiberException {
		Session session = factory.getCurrentSession();
		try {
			User tempUser = new User(user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword());
			session.beginTransaction();
			session.save(tempUser);
			session.getTransaction().commit();
			System.out.println("User created!");

		} catch (Exception e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new HiberException(e.getMessage() + " ,can't Add User");
		} finally {

			session.close();
		}
	}

	/**
	 * get User from DB
	 */
	@Override
	public User getUser(String userName) throws HiberException {
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			User tempUser = session.get(User.class, userName);
			session.getTransaction().commit();
			System.out.println("Done!");

			if (tempUser != null) {
				return tempUser;
			}

		} catch (Exception e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new HiberException(e.getMessage() + " ,can't Read this User");

		} finally {

			session.close();
		}
		throw new HiberException("Can't Read This User, Doesn't Exists");
	}

	public List<Item> getToDoList(String username) throws HiberException {

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

		} catch (Exception e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new HiberException(e.getMessage() + " ,can't Read this User and get todo list user");

		} finally {

			session.close();
		}
		throw new HiberException("Can't get item list this item list is null");

	}

	/* delete specific To Do item use id */
	public void deleteToDo_Item(int id) throws HiberException {
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

		} catch (Exception e) {

			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new HiberException(e.getMessage() + " ,can't delete This Task");

		} finally {

			session.close();
		}
		throw new HiberException("Can't delete This Task it Doesn't Exists !");

	}

	/**
	 * check if new username or email already exist in DB
	 * 
	 * @param newUserNamEmail
	 *          newUserNamEmail the new email or username that user choose in registration
	 * @param factory
	 *           factory for using DB
	 */
	public boolean isUserNameEmailExists(String newUserNamEmail) throws HiberException {

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
		} catch (Exception e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new HiberException(e.getMessage() + " ,can't check if new username or email olrady exsissst ");

		} finally {

			session.close();
		}
		return true;
	}

	/**
	 * update done value in To Do Item
	 * 
	 * @param id
	 *           id of task that need to update
	 * @param factory
	 *          factory for using DB
	 */
	public void updateItemTo_Do_done(int id) throws HiberException {
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

		} catch (Exception e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new HiberException(e.getMessage() + " ,can't update done value in This Task");

		} finally {

			session.close();
		}
		throw new HiberException("Can't Update done value This Task it Doesn't Exists !");

	}
	
	
	/**
	 *  update name and description of Task  
	 *  @param id of task that need to update
	 *  @param newToDoName new name Task
	 *  @param newToDescription new description task
	 *  @param factory for using DB
	 *  */
	public void updateItemTo_Do_Item(int id, String newToDoName, String newToDescription) throws HiberException{
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

		} catch (Exception e) {
			if (session.getTransaction() != null) {
				System.out.println("Problem creating session factory");
				session.getTransaction().rollback();
			}
			throw new HiberException(e.getMessage() + " ,can't update Task");

		} finally {

			session.close();
		}
		throw new HiberException("Can't Update This Task it Doesn't Exists !");
	}
}
