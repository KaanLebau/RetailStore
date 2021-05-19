package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import integration.DiscountDTO;
import util.enums.Category;
import util.enums.Method;

public class Sale {
	private Date saleStartTime = new Date();
	private List<Product> purcheasedProducts = new ArrayList<>();
	private List<DiscountDTO> registeredDiscount = new ArrayList<>();
	private double endSaleTotal = 0;
	private int saleId;
	private Method method;

	/**
	 * Sale constructor
	 * 
	 * @param saleId for identification of sale
	 */
	public Sale(int saleId) {
		this.saleId = saleId;
	}
	
	/**
	 * gets sale id
	 * 
	 * @return int sale id
	 */
	public int getSaleId() {
		return this.saleId;
	}

	/**
	 * checks item id with all elements in the list. if there is an element with the
	 * same item id changes that element's quantity in the product else adds new
	 * product in to the list.
	 * 
	 * uses productCheck for if product is real. uses idCheck for if there any
	 * product whit same id uses quantityUpdate for update quantity
	 * 
	 * @param itemId
	 */
	public void addProductToSale(Product product) {
		if (productCheck(product)) {
			if (idCheck(product)) {
				productQuantityUpdate(product);
			} else {
				purcheasedProducts.add(product);
			}
		}
	}

	/**
	 * Separates reel product and dummy product
	 * 
	 * @param product
	 * @return true / false
	 */

	private boolean productCheck(Product product) {
		return product.getId() != "0" ? true : false;
	}

	private boolean idCheck(Product product) {
		boolean idCheck = false;
		for (Product search : purcheasedProducts)
			if (product.getId().equalsIgnoreCase(search.getId()))
				idCheck = true;
		return idCheck;
	}

	private void productQuantityUpdate(Product product) {
		for (Product search : purcheasedProducts)
			if (product.getId() == search.getId()) {
				search.setQuantity(search.getQuantity() + product.getQuantity());
				break;
			}
	}

	/**
	 * gets purcheased product list
	 * 
	 * @return purcheasedProducts
	 */
	public List<Product> getPurcheasedProducts() {
		return purcheasedProducts;
	}

	/**
	 * gets registered discount list
	 * 
	 * @return registeredDiscount
	 */
	public List<DiscountDTO> getRegistredDiscount() {
		return registeredDiscount;
	}

	/**
	 * gets start sale date
	 * 
	 * @return date
	 */
	public Date getDate() {
		return saleStartTime;
	}

	/**
	 * adds customer discount to the registered discount list
	 * 
	 * Uses validDiscound for check discount is valid or not
	 * 
	 * @param discountRequest
	 */
	public void addCustomerDiscount(DiscountDTO discountRequest) {
		if (validDiscound(discountRequest)) {
			registeredDiscount.add(discountRequest);
		}
	}

	/**
	 * adds item/quantity discount to the registered discount list
	 * 
	 * Uses validDiscound for check discount is valid or not
	 * 
	 * @param discountRequest
	 */
	public void addItemDiscount(DiscountDTO discountRequest) {
		if (validDiscound(discountRequest) && (discountRequest.getItemQuantity() >= 1))
			for (int i = 0; i < qantityCheck(discountRequest); i++)
				registeredDiscount.add(discountRequest);
	}

	private boolean validDiscound(DiscountDTO discountRequest) {
		boolean valid = false;
		if ((discountRequest.getItemId() != "0") || (discountRequest.getDiscountId() != "0")) {
			valid = true;
		}
		return valid;
	}

	private int qantityCheck(DiscountDTO discount) {
		int quantity = 1;
		for (Product products : purcheasedProducts)
			if (products.getId().equals(discount.getItemId()))
				quantity = products.getQuantity();
		return quantity / discount.getItemQuantity();
	}

	private double totalPromotionalDiscount() {
		double promotionalDiscount = 0;
		for (DiscountDTO itemDiscount : registeredDiscount)
			if (itemDiscount.getCategory() != Category.CUSTOMER)
				promotionalDiscount += itemDiscount.getDiscountAmount();
		return promotionalDiscount;
	}

	private double priceWithPromotionalItems() {
		double priceWhitoutDiscount = 0;
		for (Product productsToPurces : purcheasedProducts)
			priceWhitoutDiscount += productsToPurces.grossPrice();
		return priceWhitoutDiscount - totalPromotionalDiscount();
	}

	private double priceWithCustomerDiscount() {
		double priceWhitDiscount = priceWithPromotionalItems();
		if (registeredDiscount.size() > 0) {
			for (DiscountDTO registred : registeredDiscount)
				if (registred.getCategory() == Category.CUSTOMER) {
					priceWhitDiscount = priceWhitDiscount * registred.getDiscountPercent();
					break;
				}
		}
		return priceWhitDiscount;
	}

	/**
	 * receives amount to pay after a purchase uses several methods to calculate
	 * totalPromotionalDiscount for amount item or quantity discount
	 * priceWithPromotionalItems for updated price with item and quantity discount
	 * priceWithCustomerDiscount if there is a customer discount adds the total
	 * 
	 * @return amount to pay
	 */
	public double getEndSaleTotal() {
		endSaleTotal = priceWithCustomerDiscount();
		return endSaleTotal;
	}
	/**
	 * close activ sale
	 */
	public void endSale(Method method) {
		
	}
}
