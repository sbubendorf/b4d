package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Country;
import beans.CountryList;
import beans.User;
import beans.Zone;

public class CountryFactory {

	private Connection conn = null;

	public CountryFactory(Connection conn) {
		this.conn = conn;
	}
	
	/*
	public List<Country> getCountries() throws SQLException {
		List<Country> cl = new ArrayList<Country>();
		String select = "select * from country";
		PreparedStatement sel = conn.prepareStatement(select);
		ResultSet rs = sel.executeQuery();
		while (rs.next()) {
			Country country = new Country();
			country.setId(rs.getInt("id"));
			country.setIso2(rs.getString("iso_2"));
			country.setIso3(rs.getString("iso_3"));
			country.setName(rs.getString("name"));
			country.setCurrency(rs.getString("currency"));
			country.setCapital(rs.getString("capital"));
			country.setContinent(rs.getString("continent"));
			country.setFlag(rs.getString("flag_file"));
			cl.add(country);
		}
		sel.close();
		rs.close();
		return cl;
		
	}
	*/
	
	public CountryList getCountries(User user) throws SQLException {
		CountryList cl = new CountryList();
		String selectCountries = 
				"select  c.*, " +
    			"        case when m.country_id is null then 0 else 1 end as supported_mover " +
				"from    country                 c " +
				"        left  join  mover_country   m   on  m.country_id = c.id " + 
				"                                        and m.mover_id = ?";
		String selectZones =
				"select  * " +
				"from    zone " +
				"where   country_id = ? " +
				"    and mover_id = ?";
		PreparedStatement selZones     = conn.prepareStatement(selectZones);
		ResultSet rsZones;
		PreparedStatement selCountries = conn.prepareStatement(selectCountries);
		selCountries.setInt(1, user.getMoverId());
		ResultSet rsCountries = selCountries.executeQuery();
		while (rsCountries.next()) {
			Country country = new Country();
			country.setId(rsCountries.getInt("id"));
			country.setIso2(rsCountries.getString("iso_2"));
			country.setIso3(rsCountries.getString("iso_3"));
			country.setName(rsCountries.getString("name"));
			country.setCurrency(rsCountries.getString("currency"));
			country.setCapital(rsCountries.getString("capital"));
			country.setContinent(rsCountries.getString("continent"));
			country.setSupportedB4D(rsCountries.getInt("supported_b4d"));
			country.setSupportedMover(rsCountries.getInt("supported_mover"));
			
			selZones.setInt(1, country.getId());
			selZones.setInt(2, user.getMoverId());
			rsZones = selZones.executeQuery();
			while ( rsZones.next() ) {
				Zone zone = new Zone();
				zone.setId(rsZones.getInt("id"));
				zone.setShortName(rsZones.getString("short_name"));
				zone.setName(rsZones.getString("name"));
				zone.setDescription(rsZones.getString("description"));
				country.addZone(zone);
			}
			
			cl.addCountry(country);
		}
		selCountries.close();
		rsCountries.close();
		return cl;
		
	}

}
