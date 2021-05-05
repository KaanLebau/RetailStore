package model;

import java.util.ArrayList;
import java.util.List;

import integration.DiscountDTO;
import util.Util.Category;

public class SaleInfoDTO {
	
	private List<Product> productsInSale = new ArrayList<>();
	private List<DiscountDTO> discountsInSale = new ArrayList<>();
	
	/**
	 *  sale information dto constructors
	 * @param product
	 * @param discount
	 */
	public SaleInfoDTO(List<Product> product,List<DiscountDTO> discount){
		this.productsInSale = product;
		this.discountsInSale = discount;
	}
	/**
	 *  gets products in active sale
	 * @return
	 */
	public List<Product> getProductsInSale() {
		return this.productsInSale;
	}
	/**
	 *  gets discounts in active sale
	 * @return
	 */
	public List<DiscountDTO> getDiscountsInSale(){
		return this.discountsInSale;
	}
	
	
	private double totalPromotionalDiscount() {
		double promotionalDiscount = 0;
		for (DiscountDTO itemDiscount : discountsInSale)
			if(itemDiscount.getCategory() != Category.CUSTOMER)
				promotionalDiscount += itemDiscount.getDiscountAmount(); 
		return promotionalDiscount;
	}
	
	private double priceWithPromotionalItems() {
		double priceWhitoutDiscount = 0;
		for (Product productsToPurces : productsInSale)
			priceWhitoutDiscount += productsToPurces.grossPrice();
		return priceWhitoutDiscount - totalPromotionalDiscount();
	}
	
	/**
	 *  gets running total in active sale
	 * @return
	 */
	public double getRuningTotal() {
		double priceWhitDiscount = priceWithPromotionalItems();
		if (discountsInSale.size() > 0) {
			for (DiscountDTO registred : discountsInSale) 
				if (registred.getCategory() == Category.CUSTOMER) {
					priceWhitDiscount = priceWhitDiscount * registred.getDiscountPercent();
					break;
				} 
		}
		return priceWhitDiscount;
	}

}
