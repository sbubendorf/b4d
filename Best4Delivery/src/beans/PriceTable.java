package beans;

import java.util.HashMap;

public class PriceTable {
	
	private Country 	countryDepa;
	private Country 	countryDest;
	private SizeRange	sizeRange;
	
	HashMap<ZonePair, Float> prices = new HashMap<ZonePair, Float>();

	public PriceTable(Country countryDepa, Country countryDest, SizeRange sizeRange) {
		this.countryDepa = countryDepa;
		this.countryDest = countryDest;
		this.sizeRange = sizeRange;
	}
	
	public Country getCountryDepa() {
		return countryDepa;
	}

	public Country getCountryDest() {
		return countryDest;
	}

	public SizeRange getSizeRange() {
		return sizeRange;
	}
	
	public void addPrice(Zone departure, Zone destination, Float price) {
		prices.put(new ZonePair(departure.getId(), destination.getId()), price);
	}
	
	public Float getPrice(Zone departure, Zone destination) {
		ZonePair key = new ZonePair(departure.getId(), destination.getId());
		return prices.get(key);
	}

	private class ZonePair {
		
		private int zoneDepaId;
		private int zoneDestId;
		
		ZonePair(int zoneDepaId, int zoneDestId) {
			this.zoneDepaId = zoneDepaId;
			this.zoneDestId = zoneDestId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + zoneDepaId;
			result = prime * result + zoneDestId;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ZonePair other = (ZonePair) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (zoneDepaId != other.zoneDepaId)
				return false;
			if (zoneDestId != other.zoneDestId)
				return false;
			return true;
		}

		private PriceTable getOuterType() {
			return PriceTable.this;
		}
	}
}
