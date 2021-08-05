package view;

import java.io.IOException;

import util.log.LogFactory;
/**
 * extends TotalRevenue who implements SaeObserver
 * sends the total sale since program started to the 
 * TotalRevenueViewLog in util.log
 * @author ozsan
 *
 */
public class TotalRevenueView extends TotalRevenue{

	/**
	 * sends total income to the log 
	 */
	@Override
	protected void showCurrentIncome(double balaceSinceTheProgramStarted, int customers) {
		LogFactory.getLogFactory().getTotalRevenueViewLog().presentIncome(balaceSinceTheProgramStarted, customers);
	}


	@Override
	protected void exceptionHandler(Exception e) throws IOException {
		
	}


}
