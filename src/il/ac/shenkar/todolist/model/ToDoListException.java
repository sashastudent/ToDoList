/**
 * Exception Handles errors of program 
 **/
package il.ac.shenkar.todolist.model;

public class ToDoListException extends Exception {


	private String eMessage;
	
	/**
	 * Constructor get message from other 
	 * exception type and set to param eMessage
	 * @param eMessage 
	 */
	public ToDoListException(String eMessage) {
		super();
		seteMessage(eMessage);
	}

	/**
	 * get eMessage
	 * @return eMessage
	 */
	public String geteMessage() {
		return eMessage;
	}

	/**
	 * set eMessage
	 * @param eMessage
	 */
	public void seteMessage(String eMessage) {
		this.eMessage = eMessage;
	}






}
