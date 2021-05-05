package model;

import java.text.DecimalFormat;

import integration.DiscountDTO;
import integration.Printer;
import util.Util.Category;
import util.Util.Method;

public class Receipt {
	Payment payment;
	Printer printer;
	String storeInformation;
	String productList;
	String discountList;
	String paymentInformation;

	public Receipt(Payment payment) {
		this.payment = payment;
	}

	/**
	 * sends receipt to printer
	 * 
	 */
	public void sendReceiptToPrinter() {		
		payment.getCashRegister().getPrinter().printOut(this);
	}
	/**
	 * information about Store name address telephone number and sale date 
	 * @return
	 */
	public String storeInformation() {
		this.storeInformation = "-----------------------------------\n";
		this.storeInformation +="- \t      " + payment.getCashRegister().getStoreName() + "       \t  -" + "\n";
		this.storeInformation += "-\t      " + payment.getCashRegister().getCity() + "   \t  -" + "\n";
		this.storeInformation += "-\t   " + payment.getCashRegister().getStreet() + " " + payment.getCashRegister().getBuildingsNumber()
				+ "   \t  -" + "\n";
		this.storeInformation += "-\t       " + payment.getCashRegister().getZipCode() + "      \t  -" + "\n";
		this.storeInformation += "-\t   " + payment.getCashRegister().getTelephone() + "\t  -" + "\n";
		this.storeInformation += "-  " + payment.getDate() + "  -" + "\n";
		this.storeInformation += "-----------------------------------";
		return this.storeInformation;
	}
	/**
	 *  information abount purchased producs in list their mane, quantity, and gross 
	 *  price to pay for each product
	 *  
	 * @return String
	 */
	public String productList() {
		return this.productList = util.Util.productList(payment.getPurchasedProductList());
	}
	/**
	 * information abount discount applyd in sale 
	 * @return string 
	 */
	public String discountList() {
		return this.discountList = util.Util.discountList(payment.getDiscountList());
	}
	/**
	 * information about payment method, amount paid and amount change
	 *  
	 * @return string
	 */
	public String paymentInformation() {
		this.paymentInformation = "* Payment Information:  \t  *\n";
		this.paymentInformation += "-----------------------------------\n";
		this.paymentInformation+= "- Amaunt to pay: " + "\t  " + (new DecimalFormat("# $,##0.00").format(payment.getTotalCost())) + " -"
				+ "\n";
		this.paymentInformation += "- Amount paid: " + "\t\t  " + (new DecimalFormat("# $,##0.00").format(payment.getAmount())) + " -"
				+ "\n";
		this.paymentInformation += "- Amount change: " + "\t   " + (new DecimalFormat("# $,##0.00").format(payment.getAmountChange()))
				+ " -" + "\n";
		if(payment.getMethod() == Method.CASH) {		
			this.paymentInformation += (this.paymentInformation += "- Paid whit: " + "\t\t     " + payment.getMethod() + " -" + "\n");
			this.paymentInformation += "-----------------------------------\n";
			return this.paymentInformation;
		} else {			
			this.paymentInformation += (this.paymentInformation += "- Paid whit: " + "\t     " + payment.getMethod() + " -" + "\n");
			this.paymentInformation += "-----------------------------------\n";
			return this.paymentInformation;
		}
	}

}
