package util.log;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.SaleInfoDTO;
import util.enums.Method;
import util.enums.ExcPriority;

public class LogMessageHandler {
	private Logger logger;
	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatClock = new SimpleDateFormat("HH:mm:ss");

	
	
	public LogMessageHandler() {
		
	}

	/**
	 * gets logger
	 *  
	 * @return logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * sets new logger
	 *  
	 * @param logger
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	/**
	 * adds information about an exception when it occurs in program Stored
	 * information exception date exception time exception message from origin Which
	 * class and method exception throws Exceptions name
	 * 
	 * @param priority      describes importance
	 * @param e             exception that logged
	 * @param exceptionName name of exception
	 */
	public void newExceptionLog(ExcPriority priority, Exception e, String exceptionName, PrintWriter exceptionLogStream) {
		String toDay = formatDate.format(new Date());
		String now = formatClock.format(new Date());
		StringBuilder exceptionLog = new StringBuilder();
		exceptionLog.append("Exception date: " + toDay);
		exceptionLog.append(" Exception time: " + now + "\n");
		exceptionLog.append(exceptionName);
		exceptionLog.append(": Exception priority: " + priority + "\n");
		exceptionLog.append(" Exception Origin: " + e.getMessage() + "\n");
		exceptionLog.toString();
		exceptionLogStream.println(exceptionLog.toString());
		exceptionLogStream.close();

	}
	
	public void newSaleLog(SaleInfoDTO saleInfo, Method method,PrintWriter saleLogStream) {
		String toDay = formatDate.format(saleInfo.getDate());
		String now = formatClock.format(saleInfo.getDate());
		StringBuilder sb = new StringBuilder();
		sb.append("Purchase id: " + saleInfo.getSaleId());
		sb.append(" Purchase date: " + toDay);
		sb.append(" Purchase time: " + now + "\n");
		sb.append("Total products in purchase: " + saleInfo.getProductsInSale().size());
		sb.append(" Total discount in purchase: " + saleInfo.getDiscountsInSale().size() + "\n");
		sb.append("Payment method: " + method);
		sb.append(" The sum of the purchase: " + saleInfo.getRuningTotal());
		saleLogStream.println(sb.toString());
		saleLogStream.close();
	}

}
