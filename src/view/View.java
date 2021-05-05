package view;

import java.text.DecimalFormat;

import controller.Controller;
import integration.DiscountDTO;
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
	
	public void scenario1() {
		controller.addProduct("101");
		updateView();
		controller.addPayment(Method.CASH, 100);
	}
	public void scenario2() {
		controller.addProduct("101", 3);
		updateView();
		//controller.discountRequest("111", "9999");
		//updateView();
		//controller.addProduct("104");
		//updateView();
		controller.addPayment(Method.CASH,100);
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
		System.out.println(util.Util.productList(saleInfoDTO.getProductsInSale()));
		System.out.println(util.Util.discountList(saleInfoDTO.getDiscountsInSale()));
		System.out.println(viewRunnunTotal());
	}


	private String viewRunnunTotal() {
		String viewRunnunTotal = "* Payment Information:  \t  *\n";
		viewRunnunTotal += "-----------------------------------\n";
		viewRunnunTotal += "- Amaunt to pay: " + "\t  "
				+ (new DecimalFormat("# $,##0.00").format(runningTotal)) + " -" + "\n";
		viewRunnunTotal += "-----------------------------------";
		return viewRunnunTotal;
	}
	 
}
