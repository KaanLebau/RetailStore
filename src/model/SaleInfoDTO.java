package model;

import java.util.Date;
import java.util.List;

import integration.DiscountDTO;

public class SaleInfoDTO {
	
	private Sale sale;
	
	public SaleInfoDTO(Sale sale){
		this.sale = sale;
	}
	/**
	 *  gets products in active sale
	 * @return
	 */
	public List<Product> getProductsInSale() {
		return sale.getPurcheasedProducts();
	}
	/**
	 *  gets discounts in active sale
	 * @return
	 */
	public List<DiscountDTO> getDiscountsInSale(){
		return sale.getRegistredDiscount();
	}
	
	/**
	 * gets sale date 
	 * @return date
	 */
	public Date getDate() {
		return sale.getDate();
	}
	
	/**
	 * gets sale id number
	 * @return sale identification nummer
	 */
	public int getSaleId() {
		return sale.getSaleId();
	}
	
	/**
	 *  gets running total in active sale
	 * @return
	 */
	public double getRuningTotal() {
		return sale.getEndSaleTotal();
	}

}
