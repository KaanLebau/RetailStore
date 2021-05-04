package integration;

//import controller.Controller;


public class ExternalAccounting {
	double account;
	//Controller ctrl;
	
	
	public ExternalAccounting() {
		
	}
	
	public void setAccount(double in) {
		this.account = in;
	}
	
	public void updateExternalAccounting(double money) {
		account += money;
	}
	public double getBalance() {
		return  this.account;
	}

}
