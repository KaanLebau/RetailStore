package util;

import java.text.DecimalFormat;
import java.util.List;

import integration.DiscountDTO;
import model.Product;

public class Util {
	/**
	 * options for discount
	 * @author ozsan
	 *
	 */
	public enum Category {
		ITEM, CUSTOMER, QUANTITY
	}
	/**
	 * options for payment
	 * @author ozsan
	 *
	 */
	public enum Method {
		CARDTERMINAL,CASH
	}
	
	/**
	 * creates a String with products purchased used for eliminate 
	 * duplicate code.
	 * @param purchasedProductList
	 * @return
	 */
	public static String productList(List<Product> purchasedProductList) {
		String productList = "* Description: \t\t\t  *\n";
		productList += "-----------------------------------\n";
		productList += "- Product" + "    quantity" + "\t price    -\n";
		for (Product registred : purchasedProductList)
			productList += "-  " + registred.getName() + "\t\t" + registred.getQuantity() + "\t  "
					+ (new DecimalFormat("# $,##0.00").format(registred.grossPrice())) + " -" + "\n";
		productList += "-----------------------------------";
		return productList;
	}

	/**
	 * creates a String with discounts in purchase used for eliminate 
	 * duplicate code.
	 * @param registredDiscount
	 * @return
	 */
	public static String discountList(List<DiscountDTO> registredDiscounts) {
		String discountList = "*      No discount to apply \t  * \n";
		if (!(registredDiscounts.size() < 1)) {
			discountList = "* Discounts:\t\t\t  *\n";
			discountList += "-----------------------------------\n";
			discountList += "- categori" + "    quantity" + "\t amount   -\n";
			for (DiscountDTO registred : registredDiscounts) {
				if (registred.getCategory() == Category.CUSTOMER) {
					discountList += "- " + registred.getCategory() + "\t" + registred.getItemQuantity() + "\t  "
							+ "-" + (new DecimalFormat("#%,##0.00").format(registred.getDiscountPercent())) + " -"
							+ "\n";
					discountList += "- Info:\t     " + registred.getDescription() + "    -" + "\n";
				} else if (registred.getCategory() == Category.QUANTITY) {
					discountList += "- " + registred.getCategory() + "\t" + registred.getItemQuantity() + "\t  "
							+ (new DecimalFormat("# $,##0.00").format(registred.getDiscountAmount())) + " -" + "\n";
					discountList += "- Info:\t\t  " + registred.getDescription() + " -" + "\n";
				} else {
					discountList += "- " + registred.getCategory() + "\t\t" + registred.getItemQuantity() + "\t  "
							+ (new DecimalFormat("# $,##0.00").format(registred.getDiscountAmount())) + " -" + "\n";
					discountList += "- Info:\t\t  " + registred.getDescription() + " -" + "\n";
				}
			}

		}
		discountList += "-----------------------------------";
		return discountList;
	}

	
}
