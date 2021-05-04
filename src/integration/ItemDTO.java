package integration;

public class ItemDTO {
	private double netPrice = 0;
	private String name = "0";
	private String id = "0";
	private double vat = 0;

	/*
	 * Product constructers
	 */

	public ItemDTO(String id, String name, double netPrice, double vat) {
		this.id = id;
		this.name = name;
		this.netPrice = netPrice;
		this.vat = vat;
	}
	
	public ItemDTO() {
		
	}

	/*
	 * Product geters
	 */
	public double getNetPrice() {
		return this.netPrice;
	}

	public String getName() {
		return this.name;
	}

	public String getId() {
		return this.id;
	}

	public double getVAT() {
		return this.vat;
	}
	
	/**
	 * compare two itemDTO if are they same item
	 * compare whith itemId 
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ItemDTO)) {
			return false;
		}
		ItemDTO other = (ItemDTO) object;
		return this.id == other.id;
	}

	public String toString() {
		String s = "";
		s = "Item = " + this.getName() + "\n";
		s = s + "Item id = " + this.getId() + "\n";
		s = s + "Item price = " + this.getNetPrice() + "\n";
		s = s + "Item VAT = " + this.getVAT() + "\n";
		return s;
	}
}
