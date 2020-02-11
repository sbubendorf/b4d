package beans;

public class PriceModel {
	
	private int id;
	private PriceModelStatus status;
	private String name;

	public PriceModel(int id, String name) {
		this(id, name, PriceModelStatus.ACTIV);
	}

	public PriceModel(int id, String name, PriceModelStatus status) {
		this.id = id;
		this.status = status;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public PriceModelStatus getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return String.valueOf(getId()) + " - " + getName() + " (" + status + ")";
	}

}
