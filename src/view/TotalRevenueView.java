package view;

import model.SaleObserver;
import util.log.LogFactory;

public class TotalRevenueView implements SaleObserver {
	private double balaceSinceTheProgramStarted = 0;

	public TotalRevenueView() {
	
	}

	@Override
	public void newSale(double income, int customers) {
		updateBalance(income);
		LogFactory.getLogFactory().getTotalRevenueViewLog().presentIncome(this.balaceSinceTheProgramStarted, customers);
	}
	private void updateBalance(double totalToPay) {
		this.balaceSinceTheProgramStarted += totalToPay;

	}
}
