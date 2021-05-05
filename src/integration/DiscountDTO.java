package integration;

import util.Util.Category;

public class DiscountDTO {

	private Category category;
	private String discountId = "0";
	private String itemId = "0";
	private String description;
	private double discountPercent = 1;
	private double discountAmount = 0;
	private int itemQuantity = 1;
	private final double FULLPRICE = 1;
	private final double WHOLEPERCENT = 100;
	private double discountInput;

	public DiscountDTO() {

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
	public DiscountDTO(Category category, String id, double discount,  String description) {
		this.category = category;
		this.description = description;
		if (category == Category.ITEM) {
			this.itemId = id;
			this.discountAmount = discount;
		} else {
			this.discountId = id;
			this.discountInput = discount;
			this.discountPercent = percentCalculater(discount);
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
	public DiscountDTO(Category category, String itemId, int itemQuantity, double discountAmount,
			String description) {
		this.category = category;
		this.itemId = itemId;
		this.itemQuantity = itemQuantity;
		this.discountAmount = discountAmount;
		this.description = description;

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
		if (!(object instanceof DiscountDTO)) {
			return false;
		}
		DiscountDTO other = (DiscountDTO) object;
		if (!(this.getCategory() == Category.CUSTOMER)) {
			return ((this.getItemId() == other.getItemId()) && (this.getCategory() == other.getCategory()));

		} else {
			return ((this.discountId == other.getDiscountId()) && (this.getCategory() == other.getCategory()));
		}

	}

	/**
	 * creates string for discountDTO
	 */
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
