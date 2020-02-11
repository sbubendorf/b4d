package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.User;

public class Account {
	
	private Connection conn = null;
	
	public Account(Connection conn) {
		this.conn = conn;
	}
	
	public User login(String email, String password) throws SQLException {
		
		User user = null;
		String select = "select * from user where email = ?";
		PreparedStatement sel = conn.prepareStatement(select);
		sel.setString(1, email);
		ResultSet rs = sel.executeQuery();
		if (rs.next()) {
			if (rs.getString("pwd").equals(password)) {
				user = new User(email, password);
				user.setUserId(rs.getInt("id"));
				user.setBirthDate(rs.getDate("date_birth"));
				user.setCity(rs.getString("city"));
				user.setCountry(rs.getString("country"));
				user.setCountryId(rs.getInt("country_id"));
				user.setFirm(rs.getString("firm"));
				user.setLastName(rs.getString("last_name"));
				user.setFirstName(rs.getString("first_name"));
				user.setGender(rs.getInt("gender_id"));
				user.setPassword(rs.getString("pwd"));
				user.setRemarks(rs.getString("remarks"));
				user.setStreet(rs.getString("street"));
				user.setStreetNo(rs.getString("street_no"));
				user.setZip(rs.getString("zip"));
				user.setMoverId(rs.getInt("mover_id"));
				user.setAdminRole(rs.getInt("admin_role"));
			}
		}
		sel.close();
		rs.close();
		return user;
	}
	
	public boolean exists(String email) throws SQLException {
		String select = "select count(*) as count from user where email = ?";
		int count = 0;
		PreparedStatement sel = conn.prepareStatement(select);
		sel.setString(1, email);
		ResultSet rs = sel.executeQuery();
		if (rs.next()) {
			count = rs.getInt("count");
		}
		sel.close();
		rs.close();
		return (count != 0);
	}
	
	public int getUserId(String email) throws SQLException {
		String select = "select id from user where email = ?";
		int userId = -1;
		PreparedStatement sel = conn.prepareStatement(select);
		sel.setString(1, email);
		ResultSet rs = sel.executeQuery();
		if (rs.next()) {
			userId = rs.getInt("id");
		}
		sel.close();
		rs.close();
		return userId;
	}
	
	public User create(User user) throws SQLException {
		
		String insert = "insert into user (email, firm, last_name, first_name, street, street_no, zip, city, country_id, pwd, remarks, gender_id, date_birth)"
				      + "          values (?    , ?   , ?        , ?         , ?     , ?        , ?  , ?   , ?      , ?  , ?      , ?        , ?         )";

		java.sql.Date dateDB = null;
		if ( user.getBirthDate() != null ) {
			dateDB = new java.sql.Date(user.getBirthDate().getTime());
		}

		PreparedStatement ins = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
		ins.setString(1, user.getEmail());
		ins.setString(2, user.getFirm());
		ins.setString(3, user.getLastName());
		ins.setString(4, user.getFirstName());
		ins.setString(5, user.getStreet());
		ins.setString(6, user.getStreetNo());
		ins.setString(7, user.getZip());
		ins.setString(8, user.getCity());
		ins.setInt(9, user.getCountryId());
		ins.setString(10, user.getPassword());
		ins.setString(11, user.getRemarks());
		ins.setInt(12, user.getGender());
		ins.setDate(13, (Date) dateDB);
		
		ins.executeUpdate();
		ResultSet userId = ins.getGeneratedKeys();
		userId.next();
		user.setUserId(userId.getInt(1));
		return user;
	}
	
	public void save(User user) throws SQLException {
		
		String insert = 
				"update user "
				+ "set email = ?, "
				+ "firm = ?, "
				+ "last_name = ?, "
				+ "first_name = ?, "
				+ "street = ?, "
				+ "street_no = ?, "
				+ "zip = ?, "
				+ "city = ?, "
				+ "country_id = ?, "
				+ "pwd = ?, "
				+ "remarks = ?, "
				+ "gender_id = ?, "
				+ "date_birth = ? "
				+ "where id = ?";
		
		java.sql.Date dateDB = null;
		if ( user.getBirthDate() != null ) {
			dateDB = new java.sql.Date(user.getBirthDate().getTime());
		}

		PreparedStatement ins = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
		ins.setString(1, user.getEmail());
		ins.setString(2, user.getFirm());
		ins.setString(3, user.getLastName());
		ins.setString(4, user.getFirstName());
		ins.setString(5, user.getStreet());
		ins.setString(6, user.getStreetNo());
		ins.setString(7, user.getZip());
		ins.setString(8, user.getCity());
		ins.setInt(9, user.getCountryId());
		ins.setString(10, user.getPassword());
		ins.setString(11, user.getRemarks());
		ins.setInt(12, user.getGender());
		ins.setDate(13, dateDB);
		ins.setInt(14, user.getUserId());
		
		ins.executeUpdate();
	}
	

}
