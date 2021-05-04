package integration;

import model.Discount;
import util.Util.Category;

public class DiscountTypDTO {

	private Category category;
	private String discountId = "0";
	private String itemId = "0";
	private String description;
	private double discountPercent = 0;
	private double discountAmount = 0;
	private final double FULLPRICE = 1;
	private final double WHOLEPERCENT = 100;
	private int itemQuantity = 1;
	private double discountInput = 1;

	public DiscountTypDTO() {
		this.category = Category.ITEM;
		this.discountId = "0";
		this.itemId = "0";
		this.discountAmount = 0;
		this.discountPercent = 0;

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
	public DiscountTypDTO(Category category, String Id, double discount, String description) {
		if (category == Category.ITEM) {
			this.category = category;
			this.itemId = Id;
			this.discountAmount = discount;
			this.description = description;
		} else {
			this.category = category;
			this.discountId = Id;
			this.discountPercent = discount;
			this.discountInput = discount;
			this.description = description;
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
	public DiscountTypDTO(Category category, String itemId, int itemQuantity, double discountAmount,
			String description) {
		this.category = category;
		this.itemId = itemId;
		this.itemQuantity = itemQuantity;
		this.discountAmount = discountAmount;
		this.description = description;

	}

	public Category getCategory() {
		return this.category;
	}

	public String getDiscountId() {
		return this.discountId;
	}

	public String getItemId() {
		return this.itemId;
	}

	public String getDescription() {
		return this.description;
	}

	public double getDiscountPercent() {
		return percentCalculater(this.discountPercent);
	}

	public double getDiscountAmount() {
		return this.discountAmount;
	}

	public int getItemQuantity() {
		return this.itemQuantity;
	}

	/**
	 * handles incoming value if needed converts to percent value
	 * 
	 * @param discount
	 * @return discount in percent form
	 */
	private double percentCalculater(double discount) {
		double discountInPercetForm;
		if (discount == 0) {
			discountInPercetForm = FULLPRICE;
		} else if (0 < discount && discount < 1) {
			discountInPercetForm = discount;
		} else {
			discountInPercetForm = FULLPRICE - (discount / WHOLEPERCENT);
		}
		return discountInPercetForm;
	}
	
	/**
	 * compare two DiscountTypDTO if are they same discount 
	 * copmare whith discoutnId
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof DiscountTypDTO)) {
			return false;
		}
		DiscountTypDTO other = (DiscountTypDTO) object;
		if(!(this.getCategory() == Category.CUSTOMER)){
			
			return ((this.getItemId() == other.getItemId()) 
					&& (this.getCategory() == other.getCategory()));
			
		}else {

			return ((this.discountId == other.getDiscountId())
					&& (this.getCategory() == other.getCategory()));
		}
		
	}

	public String toString() {
		String s = "";
		s = s + "Category " + this.category + "\n";
		s = s + "Description: " + this.description + "\n";
		s = s + "Discount id " + this.discountId + "\n";
		s = s + "Item id " + this.itemId + "\n";
		s = s + "Discount percent " + this.discountPercent + "\n";
		s = s + "Discount amount" + this.discountAmount + "\n";
		s = s + "Discount input: " + this.discountInput + "\n"; 
		s = s + "Quantity " + this.itemQuantity + "\n";

		return s;
	}

}
