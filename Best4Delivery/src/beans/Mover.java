package beans;

import java.util.ArrayList;
import java.util.List;

public class Mover {
	
	int id = 0;
	String name = null;
	double dimWeight = 0;
	PriceModel priceModel = null;
	ArrayList<SizeRange> sizeRanges = new ArrayList<SizeRange>();

	public Mover() {
	}

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

	public double getDimWeight() {
		return dimWeight;
	}

	public void setDimWeight(double dimWeight) {
		this.dimWeight = dimWeight;
	}

	public PriceModel getPriceModel() {
		return priceModel;
	}

	public void setPriceModel(PriceModel priceModel) {
		this.priceModel = priceModel;
	}
	
	public void addSizeRange(SizeRange range) {
		sizeRanges.add(range);
	}
	
	public List<SizeRange> getSizeRanges() {
		return sizeRanges;
	}
	
	public SizeRange getSizeRange(String rangeId) {
		try {
			int id = Integer.valueOf(rangeId);
			return getSizeRange(id);
		} catch (Exception e) {
			return null;
		}
	}
	
	public SizeRange getSizeRange(int rangeId) {
		for ( SizeRange range : getSizeRanges() ) {
			if ( range.getRangeId() == rangeId ) {
				return range;
			}
		}
		return null;
	}
	
}
