package util.log;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import util.enums.ExcPriority;

public class DetailedSaleLog implements Logger{
	private FileWriter fileWriter;
	private PrintWriter saleLogStream;
	ExcPriority priority;
	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatClock = new SimpleDateFormat("HH:mm:ss");

	/**
	 * does not create a new file but has access to the Exceptionlog.txt file
	 * continues to log exceptions
	 * 
	 * @throws FileNotFoundException
	 */
	public DetailedSaleLog() throws IOException {

		try {
			fileWriter = new FileWriter(
					"C:\\Users\\ozsan\\OneDrive\\Dokument\\Kth\\Programmering\\java prg\\RetailStorePOS\\log\\DetailedSaleLog.txt",
					true);
			saleLogStream = new PrintWriter(fileWriter);
		} catch (IOException e) {

			System.out.println("FileWriter in ExceptionLog constructor faild");
		}
	}

	@Override
	public void newLog(String message) {
		saleLogStream.println(message);
		saleLogStream.close();
	}
	
	public PrintWriter getPrintWriter() {
		return this.saleLogStream;
	}
}
