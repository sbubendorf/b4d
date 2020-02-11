package beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import database.DBUtility;

public class User {

	private DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	
	private int userId = -1;
	private String email = "";
	private String password = "";
	private String firm = "";
	private String lastName = "";
	private String firstName = "";
	private String street = "";
	private String streetNo = "";
	private int countryId = -1;
	private String country = "";
	private String zip = "";
	private int cityId = -1;
	private String city = "";
	private int gender;
	private Date birthDate;
	private String remarks;
	private int moverId = -1;
	private int adminRole = -1;

	private String message = "";

	public User() {
		super();
	}
	
	public User(int userId) {
		this.userId = userId;
		getUserData();
	}

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	public String getFirm() {
		return firm;
	}

	public void setFirm(String firm) {
		this.firm = firm;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	
	public void setCountryId(String countryId) {
		int ci = -1; 
		try {
			ci = Integer.parseInt(countryId);
			setCountryId(ci);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setGender(String genderId) {
		int gi = -1; 
		try {
			gi = Integer.parseInt(genderId);
			setGender(gi);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public Date getBirthDate() {
		return birthDate;
	}
	
	public String getBirthDateText() {
		String dat = "";
		if ( getBirthDate() != null ) {
			dat = df.format(getBirthDate());
		}
		return dat;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public void setBirthDate(String birthDate) {
		Date		bd = null;
		try {
			if (birthDate != null && !birthDate.equals("")) {
				bd = df.parse(birthDate);
				setBirthDate(bd);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int id) {
		this.userId = id;
	}

	public int getMoverId() {
		return moverId;
	}

	public void setMoverId(int id) {
		this.moverId = id;
	}
	
	public boolean isMover() {
		return this.moverId > 0;
	}
	
	public void setAdminRole(int adminRole) {
		this.adminRole = adminRole;
	}
	
	public boolean isAdmin() {
		return this.adminRole > 0;
	}

	public boolean validate() {

		if ("x".equals(email) && "x".equals(password)) {
			return true;
		}

		if (email == null || email.length() == 0) {
			message = "No eMail address set!";
			return false;
		}
		if (password == null || password.length() == 0) {
			message = "No password set!";
			return false;
		}

		if (!email.matches("\\w+@\\w+\\.\\w+")) {
			message = "Invalid eMail address!";
			return false;
		}

		if (password.length() < 8) {
			message = "Password must be at least 8 characters long";
			return false;
		}

		if (password.matches("\\w*\\s+\\w*")) {
			message = "Password cannot contain spaces!";
			return false;
		}

		return true;
	}

	public String getUserName() {
		return firstName + " " + lastName;
	}
	
	public void setFromRequest(HttpServletRequest request) {
		
		Map<String, String[]> params = request.getParameterMap();
		System.out.println("Setting user data:");
		for ( String key : params.keySet()) {
			String[] val = params.get(key);
			String values = "";
				for (String value : val) {
					if ( !values.equals("")) {
						values += " / ";
					}
					values += value;
				}
			System.out.println(" - " + key + " = " + values);
		}
		
		setBirthDate(request.getParameter("date_birth"));
		setCity(request.getParameter("city"));
		setCountryId(request.getParameter("country_id"));
		setEmail(request.getParameter("email"));
		setFirm(request.getParameter("firm"));
		setFirstName(request.getParameter("first_name"));
		setGender(request.getParameter("gender"));
		setLastName(request.getParameter("last_name"));
		setPassword(request.getParameter("password"));
		setRemarks(request.getParameter("remarks"));
		setStreet(request.getParameter("street"));
		setStreetNo(request.getParameter("street_no"));
		setZip(request.getParameter("zip"));
	}
	
	private void getUserData() {
		try {
			//TODO When running on Synology the following error comes up during the select execution:
			// SQLNonTransientConnectionException
			// See also http://stackoverflow.com/questions/6172930/sqlnontransientconnectionexception-no-current-connection-in-my-application-whi
			// Changed the getConnection function to get always a new connection
			String sel = "select * from user where id = ?";
			PreparedStatement ps = DBUtility.getConnection().prepareStatement(sel);
			ps.setInt(1, getUserId());
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				setFirm(rs.getString("firm"));
				setLastName(rs.getString("last_name"));
				setFirstName(rs.getString("first_name"));
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
