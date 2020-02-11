package beans;

public class SizeRange {
	
	private int rangeId;
	private int weightFrom;
	private int weightTo;

	public SizeRange(int rangeId, int weightFrom, int weightTo) {
		this.rangeId = rangeId;
		this.weightFrom = weightFrom;
		this.weightTo = weightTo;
	}

	public int getRangeId() {
		return rangeId;
	}

	public int getWeightFrom() {
		return weightFrom;
	}

	public int getWeightTo() {
		return weightTo;
	}
	
	public String getRangeLabel() {
		return String.valueOf(getWeightFrom()) + " - " + String.valueOf(getWeightTo());
	}

}
