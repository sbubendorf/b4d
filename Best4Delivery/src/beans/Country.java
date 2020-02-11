package beans;

import java.util.ArrayList;
import java.util.List;

public class Country {
	
	private int				id;
	private String			iso2;
	private String			iso3;
	private String			name;
	private String			capital;
	private String			continent;
	private String			currency;
	private String			flag;
	private int			    supportedB4D;
	private int				supportedMover;
	private List<Zone> 		zoneList = new ArrayList<Zone>();

	public Country() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIso2() {
		return iso2;
	}

	public void setIso2(String iso2) {
		this.iso2 = iso2;
	}

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public void setSupportedB4D(int suppoertedB4D) {
		this.supportedB4D = suppoertedB4D;
	}
	
	public boolean isSupportedB4D() {
		return this.supportedB4D == 1;
	}
	
	public void setSupportedMover(int suppoertedMover) {
		this.supportedMover = suppoertedMover;
	}
	
	public boolean isSupportedMover() {
		return this.supportedMover == 1;
	}
	
	public void setZoneList(List<Zone> zoneList) {
		this.zoneList = zoneList;
	}
	
	public void addZone(Zone zone) {
		zoneList.add(zone);
	}

	public List<Zone> getZoneList() {
		return zoneList;
	}
	
	public Zone getZone(int zoneId) {
		for ( Zone zone : getZoneList() ) {
			if ( zone.getId() == zoneId ) {
				return zone;
			}
		}
		return null;
	}

	public String getSupportedMoverCheck() {
		if ( isSupportedMover() ) {
			return "checked";
		} else {
			return "";
		}
	}

}
