package integration;

//import controller.Controller;


public class ExternalAccounting {
	double account;
	//Controller ctrl;
	
	
	public ExternalAccounting() {
		
	}
	/**
	 * set amount in this account
	 * @param in amount money
	 */
	public void setAccount(double in) {
		this.account = in;
	}
	/**
	 * Updates account after end sale
	 * @param money
	 */
	public void updateExternalAccounting(double money) {
		account += money;
	}
	/**
	 * gets balance in account 
	 * @return
	 */
	public double getBalance() {
		return  this.account;
	}

}
