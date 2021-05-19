package util.log;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.enums.ExcPriority;

/**
 * this is used to log all exceptions thrown from different classes and methods
 */
public class ExceptionFileLog {
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
	public ExceptionFileLog() throws IOException {

		try {
			fileWriter = new FileWriter("logFiles\\Exceptionlog.txt",true);
			exceptionLogStream = new PrintWriter(fileWriter);
		} catch (IOException e) {
			//System.out.println("FileWriter in ExceptionLog constructor faild");
			e.printStackTrace();
		}
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
	
	public void newExceptionLog(ExcPriority priority, Exception e, String exceptionName) {
		String toDay = formatDate.format(new Date());
		String now = formatClock.format(new Date());
		StringBuilder exceptionLog = new StringBuilder();
		exceptionLog.append("Exception date: " + toDay);
		exceptionLog.append(" Exception time: " + now + "\n");
		exceptionLog.append(exceptionName);
		exceptionLog.append(": Exception priority: " + priority + "\n");
		exceptionLog.append(" Exception Origin: " + e.getMessage() + "\n");
		String toLog = exceptionLog.toString();
		exceptionLogStream.println(toLog);
		exceptionLogStream.close();
		
	}
	
	
}
