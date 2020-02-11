package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import price.PriceRequest;
import database.Account;
import database.CountryFactory;
import database.DAO;
import beans.CountryList;
import beans.Mover;
import beans.PriceTable;
import beans.User;
import beans.Zone;
import beans.ZoneList;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private DataSource ds;

	private static final String DEFAULT_TARGET = "/index.jsp";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
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

		String action = request.getParameter("action");
		
		if ( action == null ) {
			request.getRequestDispatcher(DEFAULT_TARGET).forward(request, response);
		} else if ( action.equals("login") ) {
			request.setAttribute("email", "");
			request.setAttribute("password", "");
			request.setAttribute("message", "");
			request.getRequestDispatcher("signin.jsp").forward(request, response);
		} else if ( action.equals("register") ) {
			request.setAttribute("username", "");
			request.setAttribute("email", "");
			request.setAttribute("password", "");
			request.setAttribute("message", "");
			//setSessionBeans(request.getSession());
			request.getRequestDispatcher("register.jsp").forward(request, response);
		} else if ( action.equals("logout") ) {
			request.setAttribute("email", "");
			request.setAttribute("password", "");
			request.setAttribute("message", "");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else if ( action.equals("getCountries") ) {
			request.getRequestDispatcher("setcountries.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher(DEFAULT_TARGET).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

		switch (action) {
			case "doLogin":
				doLogin(request, response);
				break;
			case "doLogoff":
				doLogoff(request, response);
				break;
			case "doRegister":
				doRegister(request, response);
				break;
			case "saveProfile":
				saveProfile(request, response);
				break;
			case "getPrice":
				getPrice(request, response);
				break;
			case "saveCountries":
				saveCountries(request, response);
				break;
			case "getZones":
				getZones(request, response);
				break;
			case "getPrices":
				getPrices(request, response);
				break;
			case "saveSettings":
				saveSettings(request, response);
				break;
			case "saveZoneDetails":
				saveZoneDetails(request, response);
				break;
			default:
				request.getRequestDispatcher(DEFAULT_TARGET).forward(request, response);
		}
	}
	
    private void saveZoneDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Connection conn = null;
    	PreparedStatement ps = null;
    	try {
	    	conn = getConnection();
			String updStmnt = 
					"update  zone " +
					"set     name = ?, " +
					"		 short_name = ?, " +
					"        description = ? " +
					"where   id = ?";
			ps = conn.prepareStatement(updStmnt);
			ps.setString(1, request.getParameter("zone_name"));
			ps.setString(2, request.getParameter("zone_short_name"));
			ps.setString(3, request.getParameter("zone_remark"));
			ps.setInt(4, Integer.valueOf(request.getParameter("zone_id")));
			ps.executeUpdate();
			ps.close();
			request.setAttribute("messageType", "success");
			request.setAttribute("message", "The zone details have been saved successfully!");
		} catch (Exception e) {
			request.setAttribute("messageType", "danger");
			request.setAttribute("message", "Database error : " + e);
		} finally {
			closeConnection(conn);
		}
		request.getRequestDispatcher("/setzones.jsp").forward(request, response);
	}

	private void getZones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String country = request.getParameter("country");
		Mover mover = (Mover) request.getSession().getAttribute("mover");
    	if ( country == null || country.length() == 0 ) {
			request.setAttribute("messageType", "warning");
			request.setAttribute("message", "Please select a country!");
    	} else if ( mover == null ) {
			request.setAttribute("messageType", "danger");
			request.setAttribute("message", "Move is not set. Please log out and in again!");
    	} else {
			ZoneList list = new ZoneList();
			list.setCountryIso(country);
			list.setMoverId(mover.getId());
	    	try {
		    	conn = getConnection();
				String select = 
						"select  z.id, "
					  + "        z.name, "
					  + "        z.short_name, "
					  + "        z.description "
					  + "from    zone                z "
					  + "        inner join country  c   on  c.id = z.country_id "
					  + "where   z.mover_id = ? "
					  + "    and c.iso_2 = ?";
				ps = conn.prepareStatement(select);
				ps.setInt(1, mover.getId());
				ps.setString(2, country);
				rs = ps.executeQuery();
				while ( rs.next() ) {
					Zone zone = new Zone();
					zone.setId(rs.getInt("id"));
					zone.setName(rs.getString("name"));
					zone.setShortName(rs.getString("short_name"));
					zone.setDescription(rs.getString("description"));
					list.addZone(zone);
				}
				rs.close();
				ps.close();
				request.getSession().setAttribute("zoneList", list);
	    	} catch (Exception e) {
				request.setAttribute("messageType", "danger");
				request.setAttribute("message", "Database error : " + e);
			} finally {
				closeConnection(conn);
			}
    	}
		request.getRequestDispatcher("/setzones.jsp").forward(request, response);
	}

	private void getPrices(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String countryFrom 	= request.getParameter("country_from");
    	String countryTo   	= request.getParameter("country_to");
    	String sizeRange	= request.getParameter("size_range");
		Mover mover = (Mover) request.getSession().getAttribute("mover");
		if ( mover == null ) {
			request.setAttribute("messageType", "danger");
			request.setAttribute("message", "Mover is not set. Please log out and in again!");
		} else if ( countryFrom == null || countryFrom.length() == 0 ) {
			request.setAttribute("messageType", "warning");
			request.setAttribute("message", "Please select the country of departure!");
    	} else if ( countryTo == null || countryTo.length() == 0 ) {
			request.setAttribute("messageType", "warning");
			request.setAttribute("message", "Please select the country of destination!");
    	} else if ( sizeRange == null || sizeRange.length() == 0 ) {
			request.setAttribute("messageType", "warning");
			request.setAttribute("message", "Please select the weight range for the price table!");
    	} else {
    		CountryList cl = (CountryList)request.getSession().getAttribute("countryList");
    		PriceTable pt = new PriceTable(cl.getCountry(countryFrom), cl.getCountry(countryTo), mover.getSizeRange(sizeRange));
	    	try {
		    	conn = getConnection();
				String select = 
						"select  z.id, "
					  + "        z.name, "
					  + "        z.short_name, "
					  + "        z.description "
					  + "from    zone                z "
					  + "        inner join country  c   on  c.id = z.country_id "
					  + "where   z.mover_id = ? "
					  + "    and c.iso_2 = ?";
				ps = conn.prepareStatement(select);
				ps.setInt(1, mover.getId());
				ps.setString(2, countryFrom);
				rs = ps.executeQuery();
				while ( rs.next() ) {
					Zone zone = new Zone();
					zone.setId(rs.getInt("id"));
					zone.setName(rs.getString("name"));
					zone.setShortName(rs.getString("short_name"));
					zone.setDescription(rs.getString("description"));
				}
				rs.close();
				ps.close();
				request.getSession().setAttribute("priceTable", pt);
	    	} catch (Exception e) {
				request.setAttribute("messageType", "danger");
				request.setAttribute("message", "Database error : " + e);
			} finally {
				closeConnection(conn);
			}
    	}
		request.getRequestDispatcher("/setzones.jsp").forward(request, response);
	}

    private void saveSettings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
    	Connection conn = null;
    	PreparedStatement ps = null;
    	try {
	    	conn = getConnection();
			String updStmnt = "update mover set dim_weight = ? where id = ?";
			ps = conn.prepareStatement(updStmnt);
			Mover mover = (Mover) request.getSession().getAttribute("mover");
			if ( mover == null ) {
				request.setAttribute("message", "Current Mover bean with not set!");
			} else {
				ps.setDouble(1, Double.valueOf(request.getParameter("dim_weight")));
				ps.setInt(2, mover.getId());
				ps.executeUpdate();
				ps.close();
				setSessionBeans(request.getSession(), true);
				request.setAttribute("messageType", "success");
				request.setAttribute("message", "Your settings have been saved successfully!");
			}
		} catch (Exception e) {
			request.setAttribute("message", "Database error : " + e);
		} finally {
			closeConnection(conn);
		}
		request.getRequestDispatcher("/settings.jsp").forward(request, response);
    }
    
	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	Connection conn = getConnection();
		Account account = new Account(conn);
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = new User(email, password);
		
		if ( !user.validate() ) {
			request.setAttribute("email", "");
			request.setAttribute("password", "");
			request.setAttribute("message", user.getMessage());
			request.getRequestDispatcher("/signin.jsp").forward(request, response);
			return;
		}
		
		request.setAttribute("email", user.getEmail());
		request.setAttribute("password", user.getPassword());
		
		try {
			user = account.login(email, password);
			if ( user != null ) {
				// Login successful
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				setSessionBeans(session);
				if ( user.isMover() ) {
					request.getRequestDispatcher("/setprice.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("/getprice.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("message", "Invalid user ID or password!");
				request.getRequestDispatcher("/signin.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			request.setAttribute("message", "Database error : " + e);
			request.getRequestDispatcher("/signin.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("message", "General login error : " + e);
			request.getRequestDispatcher("/signin.jsp").forward(request, response);
		} finally {
			closeConnection(conn);
		}
    }
    
	private void doLogoff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.removeAttribute("countryList");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
    private void doRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	Connection conn = getConnection();
		Account account = new Account(conn);
		
		try {
			String message = checkUserSettings(request, account);
			if ( message.length() > 0 ) {
				request.setAttribute("messageType", "warning");
				request.setAttribute("message", message);
				request.getRequestDispatcher("/register.jsp").forward(request, response);
				return;
			} else {
				
				User user = new User();
				user.setFromRequest(request);
				account.create(user);
				request.setAttribute("messageType", "success");
				request.setAttribute("message", "You are successfully registered to B4D - Go an log in!");
				request.getRequestDispatcher("/signin.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			request.setAttribute("message", "Database error : " + e);
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		} finally {
			closeConnection(conn);
		}
    }

	
	private void saveProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	Connection conn = getConnection();
		Account account = new Account(conn);
		
		try {
			String message = checkUserSettings(request, account);
			if ( message.length() > 0 ) {
				request.setAttribute("message", message);
				request.getRequestDispatcher("/profile.jsp").forward(request, response);
				return;
			} else {
				User user = (User) request.getSession().getAttribute("user");
				user.setFromRequest(request);
				account.save(user);
				request.setAttribute("messageType", "success");
				request.setAttribute("message", "Your profile data has been saved successfully!");
				request.getRequestDispatcher("/profile.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			request.setAttribute("message", "Database error : " + e);
			request.getRequestDispatcher("/profile.jsp").forward(request, response);
		} finally {
			closeConnection(conn);
		}
    }

	private void getPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PriceRequest pr = new PriceRequest(request);
			pr.setCityDepaName(request.getParameter("city_depa"));
			pr.setCityDestName(request.getParameter("city_dest"));
			pr.calculatePrices();
			request.setAttribute("priceOffers", pr.getPriceOffers());
			request.setAttribute("priceRequest", pr);
			request.getRequestDispatcher("/showprice.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("message", "Database error : " + e);
			request.getRequestDispatcher("/getprice.jsp").forward(request, response);
		}
    }
    
    private void saveCountries(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		Connection conn = getConnection();
		String insertStmnt = "insert into mover_country (mover_id, country_id) values(?,(select id from country where iso_2 = ?))";
		String deleteStmnt = "delete from mover_country where mover_id = ?";
		try {
			PreparedStatement psDel = conn.prepareStatement(deleteStmnt);
			psDel.setInt(1, user.getMoverId());
			int rows = psDel.executeUpdate();
			psDel.close();
			System.out.println("Number of deleted countries for mover #" + user.getMoverId() + " : " + rows);
			PreparedStatement psIns = conn.prepareStatement(insertStmnt);
			Enumeration<String> paramNames = request.getParameterNames();
			while ( paramNames.hasMoreElements() ) {
				String param = (String)paramNames.nextElement();
				if ( param.startsWith("support_") ) {
					String country = param.substring(param.length()-2);
					psIns.setInt(1, user.getMoverId());
					psIns.setString(2, country);
					psIns.executeUpdate();
				}
			}
			psIns.close();
			setSessionBeans(request.getSession(), true);
			request.setAttribute("messageType", "success");
			request.setAttribute("message", "Your supported countries have been saved successfully!");
		} catch (Exception e) {
			// TODO: handle exception
			request.setAttribute("message", "Database error : " + e);
		} finally {
			closeConnection(conn);
		}
		request.getRequestDispatcher("/setcountries.jsp").forward(request, response);
	}
    
	private String checkUserSettings(HttpServletRequest request, Account account) throws SQLException {

		String message = "";
		User user = (User) request.getSession().getAttribute("user");
		user.setFromRequest(request);
		String password2 = request.getParameter("password2");

		if ( !user.validate() ) {
			message = user.getMessage();
		}
		
		if ( !user.getPassword().equals(password2)) {
			message = "The two passwords must be identical!";
		}
		
		int userId = account.getUserId(user.getEmail());
		if (userId != -1 && user.getUserId() != userId) {
			message = "This eMail is already registered!";
		}
		
		return message;
		
	}
	
    protected Connection getConnection() throws ServletException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		return conn;
    }
    
    protected void closeConnection(Connection conn) {
    	try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    private void setSessionBeans(HttpSession session) throws Exception {
    	setSessionBeans(session, false);
    }
    
    private void setSessionBeans(HttpSession session, boolean force) throws Exception {
		User user = (User) session.getAttribute("user");
    	if ( session.getAttribute("countryList") == null || force ) {
    		// Initiate country bean -------------------------------------
    		Connection conn = null;
    		try {
    			
    			conn = ds.getConnection();
    			CountryFactory cf = new CountryFactory(conn);
    			CountryList list = cf.getCountries(user);
    	    	session.setAttribute("countryList", list);
    	    	
    	    	Mover mover = (Mover) session.getAttribute("mover");
    	    	if ( mover == null ) {
    	    		mover = DAO.getInstance().getMover(user.getMoverId());
    	    	} else {
    	    		mover = DAO.getInstance().getMover(mover.getId());
    	    	}
    	    	session.setAttribute("mover", mover);
    	    	
    		} catch (SQLException e) {
    			throw e;
			} finally {
    			closeConnection(conn);
    		}
    	}
    }
    
}
