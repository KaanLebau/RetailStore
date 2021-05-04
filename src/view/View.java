package view;

import java.text.DecimalFormat;

import controller.Controller;
import model.Discount;
import model.Product;
import model.SaleInfoDTO;
import util.Util.Category;
import util.Util.Method;

public class View {
	
	private final Controller controller;
	private SaleInfoDTO saleInfoDTO;
	private double runningTotal;
	
	public View(Controller ctrl) {
		this.controller = ctrl;
	}

	public void skit() {
		
		controller.addProduct("101");
		updateView();
		System.out.println();
		controller.addProduct("101");
		controller.addProduct("101");
		updateView();
	}
	
	/**
	 * updates view with purchased product prints  product quantity, name, gross price
	 * 
	 * if any discount applied prints discount category, quantity, amount or percent and
	 * discount description
	 * 
	 * showing running total
	 * 
	 * uses viewProducts to print products
	 * uses viewDiscounts to print discounts
	 * uses viewRunnunTotal to print running total
	 * 
	 */
	public void updateView() {
		this.saleInfoDTO = controller.getSaleInfoDTO();
		this.runningTotal = controller.getSaleInfoDTO().getRuningTotal();
		clearScreen();
		System.out.println(viewProducts());
		System.out.println(viewDiscounts());
		System.out.println(viewRunnunTotal());
	}

	private String viewProducts() {
		String viewProducts = "* Description: \t\t\t  *\n";
		viewProducts += "-----------------------------------\n";
		viewProducts += "- Product" + "    quantity" + "\t price    -\n";
		for (Product registred : saleInfoDTO.getProductsInSale())
			viewProducts += "-  " + registred.getName() + "\t\t" + registred.getQuantity() + "\t  "
					+ (new DecimalFormat("# $,##0.00").format(registred.grossPrice())) + " -" + "\n";
		viewProducts += "-----------------------------------";
		return viewProducts;
	}

	private String viewDiscounts() {
		String viewDiscounts = "*      No discount to apply \t  * \n";

		if (!(saleInfoDTO.getDiscountsInSale().size() < 1)) {
			viewDiscounts = " Discounts:\n";
			viewDiscounts += "-----------------------------------\n";
			viewDiscounts += "- categori" + "    quantity" + "\t amount   -\n";
			for (Discount registred : saleInfoDTO.getDiscountsInSale()) {
				if (registred.getCategory() == Category.CUSTOMER) {
					viewDiscounts += "- " + registred.getCategory() + "\t" + registred.getItemQuantity() + "\t  " + "-"
							+ (new DecimalFormat("#%,##0.00").format(registred.getDiscountPercent())) + " -" + "\n";
					viewDiscounts += "- Info:\t     " + registred.getDescription() + "    -" + "\n";
				} else if (registred.getCategory() == Category.QUANTITY) {
					viewDiscounts += "- " + registred.getCategory() + "\t" + registred.getItemQuantity() + "\t  "
							+ (new DecimalFormat("# $,##0.00").format(registred.getDiscountAmount())) + " -" + "\n";
					viewDiscounts += "- Info:\t\t  " + registred.getDescription() + " -" + "\n";
				} else {
					viewDiscounts += "- " + registred.getCategory() + "\t\t" + registred.getItemQuantity() + "\t  "
							+ (new DecimalFormat("# $,##0.00").format(registred.getDiscountAmount())) + " -" + "\n";
					viewDiscounts += "- Info:\t\t  " + registred.getDescription() + " -" + "\n";
				}
			}
		}
		viewDiscounts += "-----------------------------------";
		return viewDiscounts;
	}

	private String viewRunnunTotal() {
		String viewRunnunTotal = "* Payment Information:  \t  *\n";
		viewRunnunTotal += "-----------------------------------\n";
		viewRunnunTotal += "- Amaunt to pay: " + "\t  "
				+ (new DecimalFormat("# $,##0.00").format(runningTotal)) + " -" + "\n";
		viewRunnunTotal += "-----------------------------------";
		return viewRunnunTotal;
	}
	public static void clearScreen() {  
	    System.out.print("");  
	    System.out.flush();  
	}  
}
