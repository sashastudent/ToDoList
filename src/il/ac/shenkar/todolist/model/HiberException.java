/**
 * Exception class use for throw Exceptions
 **/
package il.ac.shenkar.todolist.model;

public class HiberException extends Exception {

	private String eMessage;
	private Exception ex; 
	
	public HiberException() {}
			
	public void HiberExceptio (Exception ex){}
	
	public HiberException(String eMessage) {
		super();
		this.eMessage = eMessage;
	}

	public String geteMessage() {
		return eMessage;
	}

	public void seteMessage(String eMessage) {
		this.eMessage = eMessage;
	}

	public Exception getEx() {
		return ex;
	}

	public void setEx(Exception ex) {
		this.ex = ex;
	}
	


}
