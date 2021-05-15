package view;

import java.io.IOException;
import java.text.DecimalFormat;

import controller.Controller;
import model.SaleInfoDTO;
import util.enums.Method;
import util.exceptions.CustomerDiscountIdException;
import util.exceptions.CustomerRegistryException;
import util.exceptions.ItemNotFoundException;
import util.exceptions.ServerOfflineException;
import util.log.ExceptionLoger;
import util.log.LogMessageHandler;

public class View {
	
	private final Controller controller;
	private SaleInfoDTO saleInfoDTO;
	private double runningTotal;
	private LogMessageHandler logHandler;
	private ExceptionLoger exceptionLogger;
	
	public View(Controller ctrl) throws IOException {
		this.controller = ctrl;
		this.logHandler = new LogMessageHandler();
		this.exceptionLogger = new ExceptionLoger();
		controller.addSaleObserver(new TotalRevenueFileOutput());
		controller.addSaleObserver(new TotalRevenueView());
	}
	
	public void threeSale() 
			throws ServerOfflineException, ItemNotFoundException,
			CustomerDiscountIdException, CustomerRegistryException, Exception {
		try{
		controller.addProduct("101");
		updateView();
		controller.addPayment(Method.CASH, 100);
		controller.cerateNewSale();
		controller.addProduct("101");
		updateView();
		controller.addPayment(Method.CASH, 100);
		controller.cerateNewSale();
		controller.addProduct("104");
		controller.addPayment(Method.CARDTERMINAL);
		System.out.println("antal sale: " + controller.getSaleId());
		System.out.println("pengar i kassan: " + controller.getPayment().getCashRegister().getBalance());
		}catch(ItemNotFoundException e) {
			System.out.println("Wrong item id. tyr again.");
			return;
		}catch(ServerOfflineException e) {
			System.out.println("Register could not establice connection \nto the servers");
		}catch(CustomerDiscountIdException e) {
			System.out.println("Wrong discount id. Try again.");
		}catch(CustomerRegistryException e) {
			System.out.println("Customer is not registred.");
		}catch(Exception e){
			logHandler.setLogger(this.exceptionLogger);
			//logHandler.newExceptionLog(ExcPriority.HIGH, e,"UNKNOWN");
			System.out.println("Register stop working");
		}
	}
	
	
	//wrong discount id
	public void scenario1a() 
			throws ServerOfflineException, ItemNotFoundException,
			CustomerDiscountIdException, CustomerRegistryException, Exception {
		try{
		controller.addProduct("101");
		updateView();
		controller.discountRequest("11", "9999");
		controller.addPayment(Method.CASH, 100);
		}catch(ItemNotFoundException e) {
			System.out.println("Wrong item id. tyr again.");
			return;
		}catch(ServerOfflineException e) {
			System.out.println("Register could not establice connection \nto the servers");
		}catch(CustomerDiscountIdException e) {
			System.out.println("Wrong discount id. Try again.");
		}catch(CustomerRegistryException e) {
			System.out.println("Customer is not registred.");
		}catch(Exception e){
			logHandler.setLogger(this.exceptionLogger);
			//logHandler.newExceptionLog(ExcPriority.HIGH, e,"UNKNOWN");
			System.out.println("Register stop working");
		}
	}
	
	// wrong customer id customerRegister
	public void scenario1b() 
			throws ServerOfflineException, ItemNotFoundException,
			CustomerDiscountIdException, CustomerRegistryException,Exception {
		try{
		controller.addProduct("101");
		updateView();
		controller.discountRequest("111", "999");
		controller.addPayment(Method.CASH, 100);
		}catch(ItemNotFoundException e) {
			System.out.println("Wrong item id. tyr again.");
			return;
		}catch(ServerOfflineException e) {
			System.out.println("Register could not establice connection \nto the servers");
		}catch(CustomerDiscountIdException e) {
			System.out.println("Wrong discount id. Try again.");
		}catch(CustomerRegistryException e) {
			System.out.println("Customer is not registred.");
		}catch(Exception e){
			logHandler.setLogger(this.exceptionLogger);
			//logHandler.newExceptionLog(ExcPriority.HIGH, e,"UNKNOWN");
			System.out.println("Register stop working");
		}
	}
	
	public void scenario1c() 
			throws ServerOfflineException, ItemNotFoundException,
			CustomerDiscountIdException, CustomerRegistryException, IOException {
		try{
		controller.addProduct("10");
		updateView();
		controller.discountRequest("111", "9999");
		controller.addPayment(Method.CASH, 100);
		}catch(ItemNotFoundException e) {
			System.out.println(e.getMessage());
			System.out.println("Wrong item id. tyr again.");
			return;
		}catch(ServerOfflineException e) {
			System.out.println("Register could not establice connection \nto the servers");
		}catch(CustomerDiscountIdException e) {
			System.out.println("Wrong discount id. Try again.");
		}catch(CustomerRegistryException e) {
			System.out.println("Customer is not registred.");
		}catch(Exception e) {
			logHandler.setLogger(this.exceptionLogger);
			//logHandler.newExceptionLog(ExcPriority.HIGH, e,"UNKNOWN");
			System.out.println("Register stop working");
		}
	}
	
	
	public void scenario2() 
			throws ServerOfflineException, ItemNotFoundException,
			CustomerDiscountIdException, CustomerRegistryException,Exception {
		controller.addProduct("101", 9);
		updateView();
		controller.discountRequest("111", "999");
		controller.addProduct("104");
		updateView();
		controller.addPayment(Method.CARDTERMINAL);
		
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
		//System.out.print("\033[H\033[2J");  
		System.out.flush();  
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
