package util.log;

import java.io.IOException;

import view.TotalRevenueFileOutput;
import view.TotalRevenueView;

/**
 * this pattenr used for create diffrent logger
 * from one class 
 * @author ozsan
 *
 */
public class LogFactory {
	
	private static final LogFactory LOGFACTORY_SINGEL_INSTANCE = new LogFactory();

	/**
	 * log factory creator 
	 */
	private LogFactory() {
		
	}
	/**
	 * 
	 * @return instance of logfactory
	 */
	public static LogFactory getLogFactory() {
		return LOGFACTORY_SINGEL_INSTANCE;
	}
	
	public ExceptionFileLog getExceptionLogger() throws IOException {
		return new ExceptionFileLog();
		
	}
	
	public TotalRevenueFileOutputLog getTotalRevenueFileOutput() throws IOException {
		return new TotalRevenueFileOutputLog();
	}
	
	public TotalRevenueViewLog getTotalRevenueViewLog() {
		return new TotalRevenueViewLog();
	}
	
	public ChangeAmountLog getChangeAmountLog() {
		return new ChangeAmountLog();
	}
	
	public MoneyRunningLowLog getMoneyRunningLowLog() {
		return new MoneyRunningLowLog();
	}
}
