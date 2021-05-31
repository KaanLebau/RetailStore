package view;

import java.io.IOException;

import model.SaleObserver;

/**
 * super class for TotalRevenueView and TotalRevenueFileOutput
 * contains the primitive data income and an method update the income 
 * when an new sale completed
 * @author ozsan
 *
 */
public abstract class TotalRevenue implements SaleObserver {
	private double balaceSinceTheProgramStarted;
	
	protected TotalRevenue() {
		
	}
	
	/**
	 * informed by SaleObserver when an Sale closed. 
	 * uses for calculation updateBalance
	 * 
	 * @throws IOException
	 */
	@Override
	public void newSale(double totalToPay, int customers) throws IOException {
		try {
			updateBalance(totalToPay);
			showCurrentIncome(balaceSinceTheProgramStarted, customers);
		} catch (Exception e) {
			exceptionHandler(e);
		}
	}
	
	private void updateBalance(double income) {
		balaceSinceTheProgramStarted += income;

	}
	
	protected abstract void showCurrentIncome(double balaceSinceTheProgramStarted, int customers) throws IOException;
	protected abstract void exceptionHandler(Exception e) throws IOException;
}
