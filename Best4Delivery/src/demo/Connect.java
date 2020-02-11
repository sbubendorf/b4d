package demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Connect
 */
public class Connect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Connect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			out.println("Error loading Driver class!<br/>");
			out.println(e.toString());
		}
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://192.168.1.222:3306/b4d","javauser", "data4java");
			Statement select = conn.createStatement();
			ResultSet rs = select.executeQuery("select * from user");
			while ( rs.next() ) {
				out.println(rs.getInt("id") + " : " + rs.getString("name"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.println("Error when getting Connection!<br/>");
			out.println(e.toString());
		}
		
		
		try {
			if ( conn != null ) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.println("Error closing database!");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
