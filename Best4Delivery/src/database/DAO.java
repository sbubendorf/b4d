package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.City;
import beans.Country;
import beans.Mover;
import beans.PriceModel;
import beans.PriceTable;
import beans.SizeRange;
import price.Price;
import price.PriceOffer;
import price.PriceRequest;
import price.PriceSheet;

public class DAO {

	private Connection connection;
	private static DAO dao = null;

	private DAO() throws Exception {
		connection = DBUtility.getConnection();
	}
	
	public static DAO getInstance() throws Exception {
		if ( dao == null ) {
			dao = new DAO();
		}
		return dao;
	}
	
	public ArrayList<String> getCountry(String term) {
		ArrayList<String> list = new ArrayList<String>();
		PreparedStatement ps = null;
		String data;
		try {
			String select = "select * from country where lower(name) like ?";
			ps = connection.prepareStatement(select);
			ps.setString(1, term.toLowerCase() + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				data = rs.getString("name");
				list.add(data);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public List<City> getCity(String term, String country) {
		List<City> cityList = new ArrayList<City>();
		PreparedStatement ps = null;
		try {
			String select = 
					  "select  s.id, "
					+ "        s.zip, " 
					+ "        s.city_name, " 
					+ "        c.name as country_name, "
					+ "        c.iso_2 as country_iso "
					+ "from    city s "
					+ "        inner join country c on c.id = s.country_id "
					+ "where   (lower(s.city_name) like ? or s.zip like ?) "
					+ "    and c.supported_b4d = 1";
			
			if ( country != null && country.length() > 0 ) {
				select += "    and c.iso_2 = ?";
			}
			ps = connection.prepareStatement(select);
			ps.setString(1, "%" + term.toLowerCase() + "%");
			ps.setString(2, term + "%");
			if ( country != null && country.length() > 0 ) {
				ps.setString(3, country);
			}
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				City city = new City();
				city.setId(rs.getInt("id"));
				city.setZip(rs.getString("zip"));
				city.setName(rs.getString("city_name"));
				city.setCountry_iso(rs.getString("country_iso"));
				cityList.add(city);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return cityList;
	}
	
	public void savePriceSheet(PriceSheet prices) throws SQLException {
		// Delete existing prices -------------
		String deleteStatement = 
				"delete  pric " +
				"from    zone_price          pric " +
				"        inner join  zone    zon1    on  zon1.id = pric.zone_1_id " +
				"        inner join  country cou1    on  cou1.id = zon1.country_id " +
				"        inner join  zone    zon2    on  zon2.id = pric.zone_2_id " +
				"        inner join  country cou2    on  cou2.id = zon2.country_id " +
				"where   cou1.iso_2 = ? " +
				"    and cou2.iso_2 = ? " +
				"    and pric.price_model_id = ?";
		PreparedStatement ps = connection.prepareStatement(deleteStatement);
		ps.setString(1, prices.getCountryFrom());
		ps.setString(2, prices.getCountryTo());
		ps.setInt(3, prices.getPriceModelId());
		int ret = ps.executeUpdate();
		System.out.println("Number of deleted prices: " + ret);
		
		String insertStatement = "insert into zone_price (zone_1_id, zone_2_id, price_model_id, size_id, price) "
				+ "values ("
				+ "(select z.id from zone z inner join country c on c.id = z.country_id where z.mover_id = ? and c.iso_2 = ? and z.short_name = ?), "
				+ "(select z.id from zone z inner join country c on c.id = z.country_id where z.mover_id = ? and c.iso_2 = ? and z.short_name = ?), "
				+ "?, ?, ?)";
		ps = connection.prepareStatement(insertStatement);
		
		ps.setInt(1, prices.getMoverId());
		ps.setString(2, prices.getCountryFrom());
		ps.setInt(4, prices.getMoverId());
		ps.setString(5, prices.getCountryTo());
		ps.setInt(7, prices.getPriceModelId());
		ArrayList<Price> priceList = prices.getPriceList();
		for ( Price price : priceList ) {
			ps.setString(3, price.getZoneFrom());
			ps.setString(6, price.getZoneTo());
			ps.setInt(8, price.getSizeRange());
			ps.setDouble(9, price.getPrice());
			ret = ps.executeUpdate();
		}
		
		ps.close();

	}

	public List<PriceOffer> getPrices(PriceRequest priceRequest) {
		List<PriceOffer> retval = new ArrayList<PriceOffer>();
		retval.add(generateOffer(3, "Shipper AG Liestal", 245));
		retval.add(generateOffer(2, "Mover AG Basel", 263));
		retval.add(generateOffer(4, "Transporter GmbH, Platteln", 286));
		return retval;
		
	}
	
	private PriceOffer generateOffer(int moverId, String moverName, int price) {
		PriceOffer offer = new PriceOffer();
		Mover mover = new Mover();
		mover.setId(moverId);
		mover.setName(moverName);
		offer.setMover(mover);
		offer.setPrice(price);
		return offer;
	}

	public Mover getMover(int moverId) throws SQLException {
		Mover mover = null;
		String selectMover = 
				"select  m.id," +
				"        m.name," +
				"        m.dim_weight," +
				"        p.id as price_model_id, " +
				"        p.name as price_model_name " +
				"from    mover                   m " +
				"        inner join price_model  p   on p.mover_id = m.id " +
				"where   m.id = ? " +
				"    and p.status = 1";
		String selectSizes =
				"select  s.* " +
				"from    price_model		p" +
				"        inner join  size   s   on  s.price_model_id = p.id " +
				"where	 p.mover_id = ? " +
				"    and p.status = 1 " +
				"order	 by  s.weight_from";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(selectMover);
			ps.setInt(1, moverId);
			rs = ps.executeQuery();
			if (rs.next()) {
				mover = new Mover();
				mover.setId(moverId);
				mover.setName(rs.getString("name"));
				mover.setDimWeight(rs.getDouble("dim_weight"));
				mover.setPriceModel(new PriceModel(rs.getInt("price_model_id"), rs.getString("price_model_name")));
			}
			rs.close();
			// Get list of current weight ranges ---------------------------
			ps = connection.prepareStatement(selectSizes);
			ps.setInt(1, moverId);
			rs = ps.executeQuery();
			while (rs.next()) {
				SizeRange sr = new SizeRange(rs.getInt("id"), rs.getInt("weight_from"), rs.getInt("weight_to"));
				mover.addSizeRange(sr);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			try { rs.close(); } catch (Exception e) {}
		}
		return mover;
	}
	
	public PriceTable getPriceTable(Mover mover, Country countryDepa, Country countryDest, SizeRange sizeRange) throws SQLException {
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	PriceTable pt = new PriceTable(countryDepa, countryDest, sizeRange);
		String select =
				"select  zp.id, " +
				"        zp.zone_1_id, " +
				"        zp.zone_2_id, " +
				"        zp.price " +
				"from    price_model             pm " +
				"        inner join  zone_price  zp  on  zp.price_model_id = pm.id " +
				"        inner join  zone        z1  on  z1.id = zp.zone_1_id " +
				"        inner join  country     c1  on  c1.id = z1.country_id " +
				"        inner join  zone        z2  on  z2.id = zp.zone_2_id " +
				"        inner join  country     c2  on  c2.id = z2.country_id " +
				"where   pm.mover_id = ? " +
				"    and c1.id = ? " +
				"    and c2.id = ? " +
				"    and zp.size_id = ?";
		ps = connection.prepareStatement(select);
		ps.setInt(1, mover.getId());
		ps.setInt(2, countryDepa.getId());
		ps.setInt(3, countryDest.getId());
		ps.setInt(4, sizeRange.getRangeId());
		rs = ps.executeQuery();
		while ( rs.next() ) {
			pt.addPrice(countryDepa.getZone(rs.getInt("zone_1_id")), countryDest.getZone(rs.getInt("zone_2_id")), rs.getFloat("price"));
		}
		rs.close();
		ps.close();
		return pt;
	}
}
