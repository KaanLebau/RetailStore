package util;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

/**
 * this is used to log all exceptions thrown from different classes and methods
 */
public class ExceptionLoger implements Logger {
	private FileWriter fileWriter;
	private PrintWriter exceptionLogStream;
	ExcPriority priority;
	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatClock = new SimpleDateFormat("HH:mm:ss");

	/**
	 * does not create a new file but has access to the Exceptionlog.txt file
	 * continues to log exceptions
	 * 
	 * @throws FileNotFoundException
	 */
	public ExceptionLoger() throws IOException {

		try {
			fileWriter = new FileWriter(
					"C:\\Users\\ozsan\\OneDrive\\Dokument\\Kth\\Programmering\\java prg\\RetailStorePOS\\log\\Exceptionlog.txt",
					true);
			exceptionLogStream = new PrintWriter(fileWriter);
		} catch (IOException e) {

			System.out.println("FileWriter in ExceptionLog constructor faild");
		}
	}

	@Override
	public void newLog(String message) {
		exceptionLogStream.println(message);
		exceptionLogStream.close();
	}
	
	public PrintWriter getPrintWriter() {
		return this.exceptionLogStream;
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
//	public void newExceptionLog(ExcPriority priority, Exception e, String exceptionName) {
//		String toDay = formatDate.format(new Date());
//		String now = formatClock.format(new Date());
//		StringBuilder sb = new StringBuilder();
//		sb.append("Exception date: " + toDay);
//		sb.append(" Exception time: " + now + "\n");
//		sb.append(exceptionName);
//		sb.append(": Exception priority: " + priority + "\n");
//		sb.append(" Exception Origin: " + e.getMessage() + "\n");
//		exceptionLogStream.println(sb.toString());
//		exceptionLogStream.close();
//
//	}
}
