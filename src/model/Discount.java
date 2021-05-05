package model;

import util.Util.Category;

public class Discount {

	private Category category;
	private String discountId = "0";
	private String itemId = "0";
	private String description;
	private double discountPercent = 1;
	private double discountAmount = 0;
	private int itemQuantity = 1;
	private final double FULLPRICE = 1;
	private final double WHOLEPERCENT = 100;
	@SuppressWarnings("unused")
	private Sale sale;
	private double discountInput;

	public Discount() {

	}

	/**
	 * Discount constructor for for singel item and customerdiscount depending on
	 * the Category id is saved as either ItemId or DiscountID. discount is saved
	 * either discoundPercent or discountAmount
	 * 
	 * @param category ITEM / CUSTOMER
	 * @param Id       itemId / discountId
	 * @param discount discountAmout / discountPercent
	 */
	public Discount(Category category, String id, double discount, Sale sale, String description) {
		this.sale = sale;
		this.category = category;
		this.description = description;
		if (category == Category.ITEM) {
			this.itemId = id;
			this.discountAmount = discount;
			sale.addItemDiscount(this);
		} else {
			this.discountId = id;
			this.discountInput = discount;
			this.discountPercent = percentCalculater(discount);
			sale.addCustomerDiscount(this);
		}

	}

	/**
	 * Discount constructor for quantity Discount
	 * 
	 * @param category
	 * @param itemId
	 * @param itemQuantity
	 * @param discountAmount
	 */
	public Discount(Category category, String itemId, int itemQuantity, double discountAmount, Sale sale,
			String description) {
		this.category = category;
		this.itemId = itemId;
		this.itemQuantity = itemQuantity;
		this.discountAmount = discountAmount;
		this.description = description;
		this.sale = sale;
		sale.addItemDiscount(this);

	}

	/**
	 * Gets discount identification
	 * 
	 * @return String
	 */
	public String getDiscountId() {
		return this.discountId;
	}

	/**
	 * Gets discount category
	 * 
	 * @return Enum category
	 */
	public Category getCategory() {
		return this.category;
	}

	/**
	 * if discount is base of percent returns discount percent else returns 1
	 * 
	 * @return double
	 */
	public double getDiscountPercent() {
		return this.discountPercent;
	}

	/**
	 * if discount base of an sum returns that sum else return 0
	 * 
	 * @return double
	 */
	public double getDiscountAmount() {
		return this.discountAmount;
	}

	/**
	 * if discount depends on an item sends item identification else returns "0"
	 * 
	 * @return string
	 */
	public String getItemId() {
		return this.itemId;
	}

	/**
	 * if discount depends on quantity of items returns how many item purchased for
	 * discount request
	 * 
	 * @return int
	 */
	public int getItemQuantity() {
		return this.itemQuantity;
	}

	/**
	 * gets discount description
	 * 
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * sets category for discount
	 * 
	 * @param category
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * Checks category if category is correct sets discount identification else sets
	 * default value
	 * 
	 * @param discountId String
	 */
	public void setDiscountId(String discountId) {
		if (Category.CUSTOMER == this.category)
			this.discountId = discountId;
	}

	/**
	 * Checks category if category is correct sets discount percent else sets
	 * default value
	 * 
	 * @param discountPercent double
	 */
	public void setDiscountPercent(double discountPercent) {
		if (Category.CUSTOMER == this.category)
			this.discountPercent = percentCalculater(discountPercent);
	}

	/**
	 * Checks category if category is correct sets discount amount else sets default
	 * value
	 * 
	 * @param discountAmount double
	 */
	public void setDiscountAmount(double discountAmount) {
		if (this.getCategory() != Category.CUSTOMER)
			this.discountAmount = discountAmount;
	}

	/**
	 * Checks category if category is correct sets discount item identification else
	 * sets default value
	 * 
	 * @param itemId String
	 */
	public void setItemId(String itemId) {
		if (this.getCategory() != Category.CUSTOMER)
			this.itemId = itemId;
	}

	/**
	 * sets information about discount
	 * 
	 * @param description String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Checks category if category is correct sets intem quantity for discount
	 * 
	 * @param itemQuantity int
	 */
	public void setItemQuantity(int itemQuantity) {
		if (Category.QUANTITY == this.category)
			this.itemQuantity = itemQuantity;
	}

	/**
	 * sets the sale discount to add
	 * 
	 * @param sale
	 */
	public void setSale(Sale sale) {
		this.sale = sale;
	}

	private double percentCalculater(double discount) {
		if (discount == 0) {
			return FULLPRICE;
		} else if (0 < discount && discount < 1) {
			return discount;
		} else {
			return FULLPRICE - (discount / WHOLEPERCENT);
		}
	}

	/**
	 * compare two Discount if are they same discount compare whith discountId
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Discount)) {
			return false;
		}
		Discount other = (Discount) object;
		if (!(this.getCategory() == Category.CUSTOMER)) {
			return ((this.getItemId() == other.getItemId()) && (this.getCategory() == other.getCategory()));

		} else {
			return ((this.discountId == other.getDiscountId()) && (this.getCategory() == other.getCategory()));
		}

	}

	public String toString() {
		String s = "";
		s = s + "discount Category: " + this.category + "\n";
		s = s + "Discount id: " + this.discountId + "\n";
		s = s + "Item id: " + this.itemId + "\n";
		s = s + "Discount amount: " + this.discountAmount + "\n";
		s = s + "Discount percent: " + this.discountPercent + "\n";
		s = s + "Discount input; " + this.discountInput + "\n";
		s = s + "Item quantity: " + this.itemQuantity + "\n\n";
		return s;
	}
}
