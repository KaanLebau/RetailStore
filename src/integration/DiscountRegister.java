package integration;

import java.util.ArrayList;
import java.util.List;

import util.Util.Category;

public class DiscountRegister {

	private List<DiscountTypDTO> discountRegister = new ArrayList<>();

	/**
	 * Discount Register constructor dont take any argument
	 */
	public DiscountRegister() {
		DiscountTypDTO typ1 = new DiscountTypDTO(Category.ITEM, "104", 10, "Campaing product");
		DiscountTypDTO typ2 = new DiscountTypDTO(Category.CUSTOMER, "456", 12, "12% discount ");
		DiscountTypDTO typ3 = new DiscountTypDTO(Category.CUSTOMER, "111", 25, "Loyality Discount");
		DiscountTypDTO typ4 = new DiscountTypDTO(Category.CUSTOMER, "123", 1, "New member discount");
		DiscountTypDTO typ5 = new DiscountTypDTO(Category.QUANTITY, "101", 3, 11, "Buy 3 pay for 2");
		discountRegister.add(typ1);
		discountRegister.add(typ2);
		discountRegister.add(typ3);
		discountRegister.add(typ4);
		discountRegister.add(typ5);
	}

	/**
	 * updates new discount list
	 * 
	 * @param list DiscountTypDTO
	 */
	public void updateList(List<DiscountTypDTO> list) {
		this.discountRegister = list;
	}

	
	/**
	 * gets discount list
	 * 
	 * @return
	 */
	public List<DiscountTypDTO> getList() {
		return this.discountRegister;
	}

	/**
	 * searchCustomerDiscount for registered customer only create an dummy discount
	 * Search throw the discount register if discountId exist and requirement
	 * measured copy discount to dummy discount
	 * 
	 * @param discountId      used for discount identification
	 * @param customerControl checks requirement
	 * @return dummy discount / real discount
	 */
	public DiscountTypDTO searchCustomerDiscount(String discountId, boolean customerControl) {
		DiscountTypDTO foundDiscount = new DiscountTypDTO();
		if (customerControl) {
			for (DiscountTypDTO matchingDiscount : discountRegister)
				if ((matchingDiscount.getCategory() == Category.CUSTOMER)
						&& matchingDiscount.getDiscountId().equals(discountId))
					foundDiscount = matchingDiscount;
			return foundDiscount;
		}
		return foundDiscount;
	}

	/**
	 * searchItemDiscount for purchased products only create an dummy discount
	 * Search throw the discount register if there is a discount with purchased
	 * itemId returns the discount. if the number of items also matches the
	 * purchased item, then returns quantity discount
	 * 
	 * @param itemId   for product identification
	 * @param quantity for compare number of goods
	 * @return dummy discount / real discount
	 */
	public DiscountTypDTO searchItemDiscount(String itemId, int quantity) {
		DiscountTypDTO dummyDiscount = new DiscountTypDTO();
		for (DiscountTypDTO matchingDiscount : discountRegister)
			if ((matchingDiscount.getCategory() != Category.CUSTOMER)
					&& (matchingDiscount.getItemId().equalsIgnoreCase(itemId))
					&& (matchingDiscount.getItemQuantity() == quantity)) {
				return matchingDiscount;
			}
		return dummyDiscount;
	}

}
