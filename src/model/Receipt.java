package model;

import java.text.DecimalFormat;

import integration.Printer;
import util.Util.Method;

public class Receipt {
	Payment payment;
	Printer printer;
	String storeInformation;
	String productList;
	String discountList;
	String paymentInformation;
	String paymentMethod;

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
		return this.paymentInformation += "- Amount change: " + "\t   " + (new DecimalFormat("# $,##0.00").format(payment.getAmountChange()))
				+ " -";
	}

	public String paymentMetod() {
		this.paymentMethod = "";
		if(payment.getMethod() == Method.CASH) {		
			this.paymentMethod += (this.paymentMethod += "- Paid whit: " + "\t\t     " + payment.getMethod() + " -" + "\n");
			this.paymentMethod += "-----------------------------------\n";
			return this.paymentMethod;
		} else {			
			this.paymentMethod = (this.paymentMethod += "- Paid whit: " + "\t     " + payment.getMethod() + " -" + "\n");
			this.paymentMethod += "-----------------------------------\n";
			return this.paymentMethod;
		}
	}
	
}
