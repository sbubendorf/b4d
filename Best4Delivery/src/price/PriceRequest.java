package price;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import database.DAO;

public class PriceRequest {
	
	private int cityDepaId = 0;
	private int	cityDestId = 0;
	private int weight = 0;
	private int width = 0;
	private int length = 0;
	private int height = 0;
	private String cityDepaName = null;
	private String cityDestName = null;
	
	List<PriceOffer> priceOffers = new ArrayList<PriceOffer>();

	public PriceRequest(int cityDepaId, int cityDestId, int weight, int width, int depth, int height) {
		setCityDepaId(cityDepaId);
		setCityDestId(cityDestId);
		setWeight(weight);
		setWidth(width);
		setLength(depth);
		setHeight(height);
	}
	
	public PriceRequest(HttpServletRequest request) {
		setCityDepaId(Integer.valueOf(request.getParameter("city_depa_id")));
		setCityDestId(Integer.valueOf(request.getParameter("city_dest_id")));
		setWeight(Integer.valueOf(request.getParameter("weight")));
		setWidth(Integer.valueOf(request.getParameter("width")));
		setLength(Integer.valueOf(request.getParameter("length")));
		setHeight(Integer.valueOf(request.getParameter("height")));
	}
	
	public int getCityDepaId() {
		return cityDepaId;
	}

	public void setCityDepaId(int cityDepa) {
		this.cityDepaId = cityDepa;
	}

	public int getCityDestId() {
		return cityDestId;
	}

	public void setCityDestId(int cityDest) {
		this.cityDestId = cityDest;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public String getCityDepaName() {
		return cityDepaName;
	}

	public void setCityDepaName(String cityDepaName) {
		this.cityDepaName = cityDepaName;
	}

	public String getCityDestName() {
		return cityDestName;
	}

	public void setCityDestName(String cityDestName) {
		this.cityDestName = cityDestName;
	}

	public void calculatePrices() throws Exception {
		this.priceOffers = DAO.getInstance().getPrices(this);
	}
	
	public List<PriceOffer> getPriceOffers() throws Exception {
		if ( this.priceOffers == null || this.priceOffers.size() == 0 ) {
			calculatePrices();
		}
		return this.priceOffers;
	}
	

}
