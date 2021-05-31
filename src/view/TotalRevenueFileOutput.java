package view;

import java.io.IOException;

import util.enums.ExcPriority;
import util.log.LogFactory;

/**
 * extends TotalRevenue who implements SaeObserver
 * sends the total sale since program started to the 
 * TotalRevenueFileOutLog in util.log
 * @author ozsan
 *
 */

public class TotalRevenueFileOutput extends TotalRevenue{
	/**
	 * sends total income to the TotalRevenueFileOutLog
	 * Through LogFactory
	 */
	@Override
	protected void showCurrentIncome(double balaceSinceTheProgramStarted, int customers) throws IOException {
		LogFactory.getLogFactory().getTotalRevenueFileOutput().presentIncome(balaceSinceTheProgramStarted, customers);
	}
	
	/**
	 * Handling the exception to log
	 */
	@Override
	protected void exceptionHandler(Exception e) throws IOException {
		LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.HIGH, e, "exceptionHandler");
	}

}
