package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import integration.DiscountDTO;
import util.enums.Method;

public class Payment {
	private Method method;
	private CashRegister cashRegister;
	private double amount;
	private SaleInfoDTO sale;
	private double amountChange = 0;
	private boolean paymentDone = false;
	private Receipt receipt;
	private List<SaleObserver> saleObservers = new ArrayList<>();

	public Payment() {
		this.amount = 0;
		
	}
	/**
	 * Payment constructor for cash payment
	 * wait is used to simulate card terminal confirmation
	 * 
	 * @param method       is CASH
	 * @param amount       amount paid
	 * @param sale         active sale
	 * @param cashRegister this cash register
	 */
	public Payment(Method method, double amount, SaleInfoDTO sale, CashRegister cashRegister) {
		this.method = method;
		this.amount = amount;
		this.sale = sale;
		this.cashRegister = cashRegister;
		wait(1000);
		paymentDone = true;
		notifyObservers();
	}

	/**
	 * Payment constructor for card payment FUTURE DEVOLOPMENT NOT IN USE
	 * 
	 * @param method       is CARDTERMINAL
	 * @param sale         active sale
	 * @param cashRegister this cash register
	 *  
	 */
	public Payment(Method method, SaleInfoDTO sale, CashRegister cashRegister)
	
	{
		this.method = method;
		this.sale = sale;
		this.amount = getTotalCost();
		this.cashRegister = cashRegister;
		paymentDone = true;
		notifyObservers();
	}
	
	private void notifyObservers() {
		for(SaleObserver saleObserver : saleObservers)
			saleObserver.newSale(sale);
	}
	
	/**
	 * adds sale observer to the
	 * saleObservers list
	 * 
	 * @param saleObs sale observer
	 */
    public void addSaleObserver(SaleObserver saleObs) {
        saleObservers.add(saleObs);
    }
    
    /**
     * adds list of sale observer to the 
     * saleobservers list
     * 
     * @param saleObserver observer list
     */
    public void addSaleObserverList(List<SaleObserver> saleObserver) {
    	saleObservers.addAll(saleObserver);
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
		return this.amount;
	}

	/**
	 * gets the list of purchased items, name, quantity and totalcost
	 * 
	 * @return List<Product>
	 */
	public List<Product> getPurchasedProductList() {
		return sale.getProductsInSale();
	}

	/**
	 * gets list of discount applied in sale
	 * 
	 * @return List<Discount>
	 */
	public List<DiscountDTO> getDiscountList() {
		return sale.getDiscountsInSale();
	}

	/**
	 * gets total cost of total sale
	 * 
	 * @return double
	 */
	public double getTotalCost() {
		return sale.getRuningTotal();
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
		return this.amountChange = amount - getTotalCost();
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
