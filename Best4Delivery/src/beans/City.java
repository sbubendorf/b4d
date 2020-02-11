package beans;

public class City {
	
	private int	id = 0;
	private String name = null;
	private String zip = null;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry_iso() {
		return country_iso;
	}

	public void setCountry_iso(String country_iso) {
		this.country_iso = country_iso;
	}

	private String country_iso = null;

	public City() {
		// TODO Auto-generated constructor stub
	}

}
