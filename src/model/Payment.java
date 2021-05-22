package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import integration.CashMachine;
import integration.DiscountDTO;
import integration.Money;
import integration.Transaction;
import integration.TransactionObserver;
import util.enums.Method;
/**
 * it is a class that handles payment options and creates receipts
 */
public class Payment {
	private Method method;
	private CashRegister cashRegister;
	private double amount = 0;
	private SaleInfoDTO saleInfoDTO;
	private double amountChange = 0;
	private boolean paymentDone = false;
	private Receipt receipt;
	private List<SaleObserver> saleObserverList = new ArrayList<>();
	private List<TransactionObserver> transactionObserverList = new ArrayList<>();
	private Transaction transaction;
	private List<Money> exchange;

	public Payment() {

	}

	/**
	 * Payment constructor for cash payment wait is used to simulate card terminal
	 * confirmation
	 * 
	 * @param method       is CASH
	 * @param amount       amount paid
	 * @param sale         active sale
	 * @param cashRegister this cash register
	 */
	public Payment(Method method, double amount, SaleInfoDTO saleInfoDTO, CashRegister cashRegister) {
		this.method = method;
		this.amount = amount;
		this.saleInfoDTO = saleInfoDTO;
		this.cashRegister = cashRegister;
		transaction = new CashMachine();
		wait(1000);
		paymentDone = true;

	}

	/**
	 * Payment constructor for card payment FUTURE DEVOLOPMENT NOT IN USE
	 * 
	 * @param method       is CARDTERMINAL
	 * @param sale         active sale
	 * @param cashRegister this cash register
	 * 
	 */
	public Payment(Method method, SaleInfoDTO saleInfoDTO, CashRegister cashRegister)

	{
		this.method = method;
		this.saleInfoDTO = saleInfoDTO;
		this.amount = getTotalCost();
		this.cashRegister = cashRegister;
		wait(1000);
		paymentDone = true;

	}

	private void notifySaleObservers() throws IOException {
		for (SaleObserver saleObserver : saleObserverList)
			saleObserver.newSale(this.saleInfoDTO.getRuningTotal(), this.saleInfoDTO.getSaleId());

	}

	private void notifyTransactionObserver() {
		for (TransactionObserver tranactionObserver : transactionObserverList)
			 tranactionObserver.changeToDisplay(this.exchange);
	}
	
	private void notifyAllObservers() throws IOException {
		notifySaleObservers();
		notifyTransactionObserver();
	}

	/**
	 * adds list of sale observer to the saleobservers list
	 * 
	 * @param saleObserver observer list
	 */
	public void addSaleObserverList(List<SaleObserver> saleObservers) {

		saleObserverList.addAll(saleObservers);
	}

	public void addObserversList(List<TransactionObserver> observers) {

		transactionObserverList.addAll(observers);
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
	 * by card payment it confirms the money transaction used in controller before
	 * update external System
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
		return saleInfoDTO.getProductsInSale();
	}

	/**
	 * gets list of discount applied in sale
	 * 
	 * @return List<Discount>
	 */
	public List<DiscountDTO> getDiscountList() {
		return saleInfoDTO.getDiscountsInSale();
	}

	/**
	 * gets total cost of total sale
	 * 
	 * @return double
	 */
	public double getTotalCost() {
		return saleInfoDTO.getRuningTotal();
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
		return saleInfoDTO.getDate();
	}

	/**
	 * calculates amount change to pay back to the customer
	 * 
	 * @return double
	 */
	public double getAmountChange() {
		return this.amountChange;
	}
	
	private void updateAmountChange() {
		this.amountChange = amount - getTotalCost();
	}

	/**
	 * create an receipt
	 * 
	 * @param payment
	 * @throws IOException 
	 */
	public void createReceipt(Payment payment) throws IOException {
		updateAmountChange();
		if(payment.getMethod() == Method.CASH) {			
			exchange = transaction.moneyFlow(amount, amountChange);
			cashRegister.getCashMachine().getRunninLow();
		}
		notifyAllObservers();
		this.cashRegister.updateCashRegister();
		this.receipt = new Receipt(payment);
	}

	/**
	 * sends receipt
	 * 
	 * @return receipt
	 */
	public Receipt getReceipt() {
		return this.receipt;
	}

	private void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			System.out.println("class Payment metod wait faild");
			Thread.currentThread().interrupt();
		}
	}
}
