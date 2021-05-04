package integration;

import model.Receipt;

public class Printer {
	
	
	public Printer() {
		
	}
	
	/**
	 *  prints receipts
	 *  
	 * @param receipt contains information about sale and store 
	 */
	public void printOut(Receipt receipt) {
		System.out.println(receipt.storeInformation());
		System.out.println(receipt.productList());
		System.out.println(receipt.discountList());
		System.out.println(receipt.paymentInformation());
	}

}
