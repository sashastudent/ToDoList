/**ToDo Item Task Class hold all details of Task
 * the class using annotations of Hibernate */
package il.ac.shenkar.todolist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Items")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private int itemId;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "done")
	private boolean done;
	@Column(name = "user_name")
	private String userName;

	/**
	 * Default constructor required for use Hibernate
	 */
	public Item() {}

	
	/**
	 * Constructor create Item Task
	 * @param name of task
	 * @param description of task
	 * @param userName or email of user its unique name in DB
	 */
	public Item(String name, String description, String userName) {
		setName(name);
		setDescription(description);
		setDone(false);
		setUserName(userName);	
	}
	
	
	/**
	 *  Getter of id Task
	 *   @return itemId id of task
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * Setter of id task
	 * @param itemId
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * Getter of task name
	 * @return task name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter of name Task
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter of description Task
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter of description Task 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Getter status value if done or not
	 * @return done
	 */
	public boolean getDone() {
		return done;
	}

	/**
	 * Setter done value 
	 * @param done
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * Getter of username or email
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Setter username or email
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Item [id=" + itemId + ", name=" + name + ", description=" + description + ", priority=" 
				+ ", Done=" + done + ", UserName=" + userName + "]";
	}

}
