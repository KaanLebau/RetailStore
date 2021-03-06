package model;

public class Product {
	private String productName;
	private String productId;
	private double netPrice;
	private double vat;
	private final double FULLPRICE = 1;
	private final double WHOLEPERCENT = 100;
	private int quantity; 
	

	/*
	 * Product constructors
	 */

	public Product(String id, String name, double netPrice, double vat, int quantity) {
		this.productId = id;
		this.productName = name;
		this.netPrice = netPrice;
		this.vat = vat;
		this.quantity = quantity;
	}
	
	/*
	 * Product constructors
	 */
	public Product(String id, String name, int quantity) {
		this.productId = id;
		this.productName = name;
		this.quantity = quantity;
	}
	/*
	 * Product constructors
	 */
	public Product() {
		
	}

	/**
	 * gets net price without VAT
	 * 
	 * @return netPrice
	 */
	public double getNetPrice() {
		return this.netPrice;
	}

	/**
	 * gets name
	 * @return
	 */
	public String getName() {
		return this.productName;
	}

	/**
	 * gets id
	 * @return
	 */
	public String getId() {
		return this.productId;
	}

	/**
	 * gets vat
	 * @return
	 */
	public double getVAT() {
		return this.vat;
	}
	/**
	 * gets quantity
	 * @return
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 *  sets quantity 
	 *  
	 * @param quantity  number of products
	 */
	public void setQuantity(int quantity) {
		if(quantity > 0)
			this.quantity = quantity;
	}
	
	/**
	 * adds to the quantity under add product to the sale 
	 * 
	 * @param quantity number of products
	 */
	public void addQuantity(int quantity) {
		if(quantity > 0)
			this.quantity += quantity;
	}
	
	/**
	 * if cashier need to adjust quantity of products
	 * 
	 * @param quantity to remove
	 */
	public void removeQuantity(int quantity) {
		if(quantity < 0)
			quantity = quantity * (-1);
		this.quantity -= quantity;
	}
	
	private double calculateVAT() {
		return (this.vat / WHOLEPERCENT) + FULLPRICE;
	}

	/*
	 * Calculates total price for Product
	 */
	public double grossPrice() {
		return this.getNetPrice() * this.getQuantity() * calculateVAT();		
	}
	
	/**
	 * compare two Product if are they same product 
	 * whith  productId
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Product)) {
			return false;
		}
		Product other = (Product) object;
		return this.productId == other.productId;
	}
	
	/**
	 * creates string for Product
	 */
	public String toString() {
		String s = "";
		s = "Produkt = " + this.getName() + "\n";
		s = s + "Product id = " + this.getId() + "\n";
		s = s + "Product price = " + this.getNetPrice() + "\n";
		s = s + "Product VAT = " + this.getVAT() + "\n";
		s = s + "Product gross price = " + this.grossPrice() + "\n";
		s = s + "Product quantity = " + this.getQuantity() + "\n";
		return s;
	}

}
