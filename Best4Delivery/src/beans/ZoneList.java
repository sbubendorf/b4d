package beans;

import java.util.ArrayList;
import java.util.List;

public class ZoneList {
	
	private String countryIso = null;
	private int moverId = 0;
	private List<Zone> zoneList = new ArrayList<Zone>();

	public ZoneList() {
		// TODO Auto-generated constructor stub
	}

	public String getCountryIso() {
		return countryIso;
	}

	public void setCountryIso(String countryIso) {
		this.countryIso = countryIso;
	}

	public int getMoverId() {
		return moverId;
	}

	public void setMoverId(int moverId) {
		this.moverId = moverId;
	}

	public List<Zone> getZoneList() {
		return zoneList;
	}

	public void setZoneList(List<Zone> zoneList) {
		this.zoneList = zoneList;
	}
	
	public void addZone(Zone zone) {
		zoneList.add(zone);
	}

}
