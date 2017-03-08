package il.ac.shenkar.todolist.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

	public static void main(String[] args) {
		
			String jdbcUrl = "jdbc:mysql://localhost:3306/inventory?useSSL=false";
			String user = "root";
			String pass = "root";
			try{
				System.out.println("Connecting to database: " +jdbcUrl);
				
				Connection myConn = DriverManager.getConnection(jdbcUrl, user, pass);
				
				System.out.println("Connection Successful");
			}
			catch(Exception exc){
				exc.printStackTrace();
			}
	}		

}
