package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	
	//private static Connection connection = null;

	public static Connection getConnection() throws Exception {
		
		Class.forName("org.mariadb.jdbc.Driver");		
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/b4d","javauser", "data4java");
		
		//		if (connection != null)
//			return connection;
//		else {
//			Class.forName("org.mariadb.jdbc.Driver");
//			// set the url, username and password for the databse
//			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/b4d","javauser", "data4java");
//			return connection;
//		}
	}
}