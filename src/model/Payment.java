package model;

import java.util.Date;
import java.util.List;

import controller.Controller;
import util.Util.Method;

public class Payment {
	private Method method;
	private CashRegister cashRegister;
	private final double AMOUNT;
	private Sale sale;
	private double amountChange = 0;
	private boolean paymentDone = false;
	private Receipt receipt;

	public Payment() {
		this.AMOUNT = 0;
		
	}
	/**
	 * Payment constructor for cash payment
	 * 
	 * @param method       is CASH
	 * @param amount       amount paid
	 * @param sale         active sale
	 * @param cashRegister this cash register
	 */
	public Payment(Method method, double amount, Sale sale, CashRegister cashRegister) {
		this.method = method;
		this.AMOUNT = amount;
		this.sale = sale;
		this.cashRegister = cashRegister;
		//this.amountChange = getAmountChange();
		wait(1000);
		paymentDone = true;;
	}

	/**
	 * Payment constructor for card payment FUTURE DEVOLOPMENT NOT IN USE
	 * 
	 * @param method       is CARDTERMINAL
	 * @param sale         active sale
	 * @param cashRegister this cash register
	 *  
	 */
	public Payment(Method method, Sale sale, CashRegister cashRegister)
	
	{
		this.method = method;
		this.sale = sale;
		this.AMOUNT = getTotalCost();
		this.cashRegister = cashRegister;
		paymentDone = true;
	}

	/**
	 * gets payment method
	 * 
	 * @return
	 */
	public Method getMethod() {
		return this.method;
	}
	/**
	 * by card payment it confirms the money transaction  
	 * used in controller before update external System
	 * 
	 * @return boolean
	 */
	public boolean getPaymentDone() {
		return this.paymentDone;
	}

	/**
	 * gets amount paid
	 * 
	 * @return double
	 */
	public double getAmount() {
		return this.AMOUNT;
	}

	/**
	 * gets the list of purchased items, name, quantity and totalcost
	 * 
	 * @return List<Product>
	 */
	public List<Product> getPurchasedProductList() {
		return sale.getPurcheasedProducts();
	}

	/**
	 * gets list of discount applied in sale
	 * 
	 * @return List<Discount>
	 */
	public List<Discount> getDiscountList() {
		return sale.getRegistredDiscount();
	}

	/**
	 * gets total cost of total sale
	 * 
	 * @return double
	 */
	public double getTotalCost() {
		return sale.getEndSaleTotal();
	}

	/**
	 * gets cash register information
	 * 
	 * @return cashRegister
	 */
	public CashRegister getCashRegister() {
		return cashRegister;
	}

	/**
	 * gets date and time of sale
	 * 
	 * @return date
	 */
	public Date getDate() {
		return sale.getDate();
	}

	/**
	 * calculates amount change to pay back to the customer
	 * 
	 * @return double
	 */
	public double getAmountChange() {
		return this.amountChange = AMOUNT - getTotalCost();
	}
	/**
	 * create an receipt 
	 * @param payment
	 */
	public void createReceipt(Payment payment) {
		this.receipt = new Receipt(payment);
	}
	/**
	 * sends receipt 
	 * @return receipt 
	 */
	public Receipt getReceipt() {
		return this.receipt;
	}

	private void wait(int ms) {
		try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
}
