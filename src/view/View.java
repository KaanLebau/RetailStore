package view;

import java.io.IOException;
import java.text.DecimalFormat;

import controller.Controller;
import model.SaleInfoDTO;
import util.enums.Method;
import util.exceptions.CustomerDiscountIdNotFoundException;
import util.exceptions.CustomerIdNotFoundException;
import util.exceptions.ItemNotFoundException;
import util.exceptions.ServerOfflineException;

public class View {

	private final Controller controller;
	private SaleInfoDTO saleInfoDTO;
	private double runningTotal;

	public View(Controller ctrl) throws IOException {
		this.controller = ctrl;
		controller.addSaleObserver(new TotalRevenueView());
		controller.addSaleObserver(new TotalRevenueFileOutput());
		controller.addTransactionObservers(new ChangeAmount());
		controller.addMoneyObservers(new MoneyRunningLow());

	}

	public void totalRevenuePrint() throws ServerOfflineException, ItemNotFoundException,
			CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		try {
			controller.cerateNewSale();
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
		} catch (ItemNotFoundException e) {
			System.out.println("Wrong item id. tyr again.");
		} catch (ServerOfflineException e) {
			System.out.println("Register could not establice connection \nto the servers");
		} catch (CustomerDiscountIdNotFoundException e) {
			System.out.println("Wrong discount id. Try again.");
		} catch (CustomerIdNotFoundException e) {
			System.out.println("Customer is not registred.");
		} catch (Exception e) {
			System.out.println("Register stop working");
		}
	}

	public void receiptCashTest() throws ServerOfflineException, ItemNotFoundException,
			CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		try {
			controller.cerateNewSale();
			controller.addProduct("101");
			updateView();
			controller.discountRequest("111", "9999");
			controller.addPayment(Method.CASH, 100);
		} catch (ItemNotFoundException e) {
			System.out.println("Wrong item id. tyr again.");
			return;
		} catch (ServerOfflineException e) {
			System.out.println("Register could not establice connection \nto the servers");
		} catch (CustomerDiscountIdNotFoundException e) {
			System.out.println("Wrong discount id. Try again.");
		} catch (CustomerIdNotFoundException e) {
			System.out.println("Customer is not registred.");
		} catch (Exception e) {
			System.out.println("Register stop working");
		}
	}

	public void receiptCARDTERMINALTest() throws ServerOfflineException, ItemNotFoundException,
			CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		try {
			controller.cerateNewSale();
			controller.addProduct("101");
			updateView();
			controller.discountRequest("111", "9999");
			controller.addPayment(Method.CARDTERMINAL);
		} catch (ItemNotFoundException e) {
			System.out.println("Wrong item id. tyr again.");
			return;
		} catch (ServerOfflineException e) {
			System.out.println("Register could not establice connection \nto the servers");
		} catch (CustomerDiscountIdNotFoundException e) {
			System.out.println("Wrong discount id. Try again.");
		} catch (CustomerIdNotFoundException e) {
			System.out.println("Customer is not registred.");
		} catch (Exception e) {
			System.out.println("Register stop working");
		}
	}

	// wrong customer id customerRegister
	public void wrongCustomerId() throws ServerOfflineException, ItemNotFoundException,
			CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		try {
			controller.addProduct("101");
			updateView();
			controller.discountRequest("111", "999");
			controller.addPayment(Method.CASH, 100);
		} catch (ItemNotFoundException e) {
			System.out.println("Wrong item id. tyr again.");
			return;
		} catch (ServerOfflineException e) {
			System.out.println("Register could not establice connection \nto the servers");
		} catch (CustomerDiscountIdNotFoundException e) {
			System.out.println("Wrong discount id. Try again.");
		} catch (CustomerIdNotFoundException e) {
			System.out.println("Customer is not registred.");
		} catch (Exception e) {
			System.out.println("Register stop working");
		}
	}

	// wrong customer id customerRegister
	public void wrongDiscountId() {
		try {
			controller.addProduct("101");
			updateView();
			controller.discountRequest("111", "999");
			controller.addPayment(Method.CASH, 100);
		} catch (ItemNotFoundException e) {
			System.out.println("Wrong item id. tyr again.");
			return;
		} catch (ServerOfflineException e) {
			System.out.println("Register could not establice connection \nto the servers");
		} catch (CustomerDiscountIdNotFoundException e) {
			System.out.println("Wrong discount id. Try again.");
		} catch (CustomerIdNotFoundException e) {
			System.out.println("Customer is not registred.");
		} catch (Exception e) {
			System.out.println("Register stop working");
		}
	}

	public void scenario1c() throws ServerOfflineException, ItemNotFoundException, CustomerDiscountIdNotFoundException,
			CustomerIdNotFoundException, IOException {
		try {
			controller.addProduct("10");
			updateView();
			controller.discountRequest("111", "9999");
			controller.addPayment(Method.CASH, 100);
		} catch (ItemNotFoundException e) {
			System.out.println("Wrong item id. tyr again.");
			return;
		} catch (ServerOfflineException e) {
			System.out.println("Register could not establice connection \nto the server");
		} catch (CustomerDiscountIdNotFoundException e) {
			System.out.println("Wrong discount id. Try again.");
		} catch (CustomerIdNotFoundException e) {
			System.out.println("Customer is not registred.");
		} catch (Exception e) {
			System.out.println("Register stop working");
		}
	}

	public void wrongItemId() throws ServerOfflineException, ItemNotFoundException, CustomerDiscountIdNotFoundException,
			CustomerIdNotFoundException, Exception {
		try {
			controller.cerateNewSale();
			controller.addProduct("101");
			updateView();
			controller.discountRequest("111", "9999");
			controller.addPayment(Method.CASH, 100);
		} catch (ItemNotFoundException e) {
			System.out.println("Wrong item id. tyr again.");
			return;
		} catch (ServerOfflineException e) {
			System.out.println("Register could not establice connection \nto the servers");
		} catch (CustomerDiscountIdNotFoundException e) {
			System.out.println("Wrong discount id. Try again.");
		} catch (CustomerIdNotFoundException e) {
			System.out.println("Customer is not registred.");
		} catch (Exception e) {
			System.out.println("Register stop working");
		}
	}

	/**
	 * updates view with purchased product prints product quantity, name, gross
	 * price
	 * 
	 * if any discount applied prints discount category, quantity, amount or percent
	 * and discount description
	 * 
	 * showing running total
	 * 
	 * uses viewProducts to print products uses viewDiscounts to print discounts
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
		viewRunnunTotal += "- Amaunt to pay: " + "\t  " + (new DecimalFormat("# $,##0.00").format(runningTotal)) + " -"
				+ "\n";
		viewRunnunTotal += "-----------------------------------";
		return viewRunnunTotal;
	}

}
