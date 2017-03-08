/**ToDo Item Class hold all details of ToDo Item
 * the class using entity */
package il.ac.shenkar.todolist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
	@Column(name = "priority")
	private int priority;
	@Column(name = "done")
	private boolean done;
	@Column(name = "user_name")
	private String userName;

	public Item() {}

	public Item(String name, String description, int priority, String userName) {
		setName(name);
		setDescription(description);
		setPriority(priority);
		setDone(false);
		setUserName(userName);	
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean getDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Item [id=" + itemId + ", name=" + name + ", description=" + description + ", priority=" + priority
				+ ", Done=" + done + ", UserName=" + userName + "]";
	}

}
