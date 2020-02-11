package price;

public class Price {
	
	private String zoneFrom;
	private String zoneTo;
	private Integer sizeRange;
	private double price;

	public Price(String zoneFrom, String zoneTo, Integer sizeRange, double price) {
		this.zoneFrom = zoneFrom;
		this.zoneTo = zoneTo;
		this.sizeRange = sizeRange;
		this.price = price;
	}

	public String getZoneFrom() {
		return zoneFrom;
	}

	public String getZoneTo() {
		return zoneTo;
	}

	public Integer getSizeRange() {
		return sizeRange;
	}

	public double getPrice() {
		return price;
	}

}
