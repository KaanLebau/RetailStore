package util.log;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import util.enums.ExcPriority;

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

}
