package integration;

import model.Receipt;

public class Printer {
	
	
	public Printer() {
		
	}
	
	
	public void printout(Receipt receipt) {
		System.out.println(receipt.storeInformation());
		System.out.println(receipt.productList());
		System.out.println(receipt.discountList());
		System.out.println(receipt.paymentInformation());
	}

}
