/**User Class hold all details of user 
 * the class using annotations of Hibernate **/
package il.ac.shenkar.todolist.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User implements Serializable{


	@Id
	@Column(unique=true, name = "user_name", nullable = false)
	private String userName;
	@Column(name = "firs_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "password")
	private String password;
	
	/**
	 * Default constructor required for use Hibernate
	 */
	public User() {}

	/**
	 * Constructor create User
	 * @param firstName of user
	 * @param lastName of user
	 * @param userName or email its unique name in DB
	 * @param password of user
	 */
	public User(String firstName, String lastName, String userName, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setUserName(userName);
		setPassword(password);
	}

	
	/**
	 * Getter of first name 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter of first name of user
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter of last name of user
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter of last name of user
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter of user name/email 
	 * @return userName/email
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Setter of username/email 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Getter of password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter of Password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [ firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", password=" + password + "]";
	}

}
