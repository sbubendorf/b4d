package beans;

public enum PriceModelStatus {
	ACTIV(1, "Active"),
	TEMP(2, "Temporary"),
	ARCHIVE(9, "Archived");
	
	private final int id;
	private final String name;
	
	PriceModelStatus(int id, String name) {
		this.id = id;
		this.name = name;
		
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
