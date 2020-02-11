package demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class DatasourceDemo
 */
public class DatasourceDemo extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private DataSource ds;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DatasourceDemo() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			InitialContext initContext = new InitialContext();
			Context env = (Context) initContext.lookup("java:comp/env");
			ds = (DataSource) env.lookup("jdbc/b4d");
		} catch (NamingException e) {
			throw new ServletException();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		Connection conn = null;
		try {
			out.println("<p>Connecting to database ...</p>");
			conn = ds.getConnection();
			out.println("<p>Connection successful : " + conn.toString() + "</p>");
			Statement sel = conn.createStatement();
			ResultSet rs = sel.executeQuery("select * from user");
			out.println("<h2>List of users</h2>");
			out.println("<table>");
			out.println("<tr><td>ID</td><td>name</td></tr>");
			while ( rs.next() ) {
				out.println("<tr><td>" + rs.getInt("id") + "</td><td>" + rs.getString("name") + "</td></tr>");
			}
			out.println("</table>");
		} catch (SQLException e) {
			out.println("Error during database access : ");
			out.println(e);
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			out.print("<p>Error when trying to close connection: " + e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
