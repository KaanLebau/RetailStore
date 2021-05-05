package model;

import java.util.ArrayList;
import java.util.List;

import integration.DiscountDTO;
import util.Util.Category;

public class SaleInfoDTO {
	
	private List<Product> productsInSale = new ArrayList<>();
	private List<DiscountDTO> discountsInSale = new ArrayList<>();
	
	public SaleInfoDTO(List<Product> product,List<DiscountDTO> discount){
		this.productsInSale = product;
		this.discountsInSale = discount;
	}
	
	public List<Product> getProductsInSale() {
		return this.productsInSale;
	}
	
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

	private double runingTotal() {
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

	public double getRuningTotal() {
		return runingTotal();
		
	}

}
