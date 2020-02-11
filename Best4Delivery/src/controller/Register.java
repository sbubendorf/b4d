package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.Email;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

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
	
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		
		String action = request.getParameter("action");
		if ( "checkEmail".equals(action)) {
			String msg = "";
			try {
				msg = checkEmail(request.getParameter("email"), conn);
			} catch (SQLException e) {
				msg = "Error while accessing database : " + e;
			}
			response.setContentType("text/plain");
		    response.getWriter().write(msg);
		}
	}

	private String checkEmail(String email, Connection conn) throws SQLException {

		String msg = "";
		Email em = new Email(email);
		if (!em.validate()) {
			msg = "The address '" + email + "' is invalid!";
		} else {
			String select = "select id from user where email = ?";
			PreparedStatement sel = conn.prepareStatement(select);
			sel.setString(1, email);
			ResultSet rs = sel.executeQuery();
			if (rs.next()) {
				msg = "Email '" + email + "' is already registered! (User ID #" + rs.getInt(1);
			}
			sel.close();
			rs.close();
		}
		return msg;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
