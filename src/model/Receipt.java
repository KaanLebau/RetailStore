package model;

import java.text.DecimalFormat;

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
		payment.getCashRegister().getPrinter().printout(this);
	}

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

	public String productList() {
		this.productList = "* Description: \t\t\t  *\n";
		this.productList += "-----------------------------------\n";
		this.productList += "- Product" + "    quantity" + "\t price    -\n";
		for (Product registred : payment.getPurchasedProductList())
			this.productList += "-  " + registred.getName() + "\t\t" + registred.getQuantity() + "\t  "
					+ (new DecimalFormat("# $,##0.00").format(registred.grossPrice())) + " -" + "\n";
		this.productList += "-----------------------------------";
		return this.productList;
	}

	public String discountList() {
		this.discountList = "*      No discount to apply \t  * \n";
		if (!(payment.getDiscountList().size() < 1)) {
			this.discountList = " Discounts:\n";
			this.discountList += "-----------------------------------\n";
			this.discountList += "- categori" + "    quantity" + "\t amount   -\n";
			for (Discount registred : payment.getDiscountList()) {
				if (registred.getCategory() == Category.CUSTOMER) {
					this.discountList += "- " + registred.getCategory() + "\t" + registred.getItemQuantity() + "\t  " + "-"
							+ (new DecimalFormat("#%,##0.00").format(registred.getDiscountPercent())) + " -" + "\n";
					this.discountList += "- Info:\t     " + registred.getDescription() + "    -" + "\n";
				} else if (registred.getCategory() == Category.QUANTITY) {
					this.discountList += "- " + registred.getCategory() + "\t" + registred.getItemQuantity() + "\t  "
							+ (new DecimalFormat("# $,##0.00").format(registred.getDiscountAmount())) + " -" + "\n";
					this.discountList += "- Info:\t\t  " + registred.getDescription() + " -" + "\n";
				} else {
					this.discountList += "- " + registred.getCategory() + "\t\t" + registred.getItemQuantity() + "\t  "
							+ (new DecimalFormat("# $,##0.00").format(registred.getDiscountAmount())) + " -" + "\n";
					this.discountList += "- Info:\t\t  " + registred.getDescription() + " -" + "\n";
				}
			}
			
		}
		this.discountList += "-----------------------------------";
		return this.discountList;
	}

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
		} else {			
			this.paymentInformation += (this.paymentInformation += "- Paid whit: " + "\t     " + payment.getMethod() + " -" + "\n");
		}
		this.paymentInformation += "-----------------------------------\n";
		return this.paymentInformation;
	}

}
