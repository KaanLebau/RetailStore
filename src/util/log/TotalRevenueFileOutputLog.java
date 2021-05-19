package util.log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * 
 * @author ozsan
 *
 */
public class TotalRevenueFileOutputLog {

	private PrintWriter totalRevenueFileLogStream;

	/**
	 * create a new .txt file every time called prints
	 */
	public TotalRevenueFileOutputLog() {

		try {

			totalRevenueFileLogStream = new PrintWriter
					(new FileWriter("logFiles\\TotalRevenueFile.txt"), true);
		} catch (IOException e) {
			System.out.println("FileWriter in TotalRevenueFileOutputLog constructor faild");
		}
	}

	public void presentIncome(double income, int customers) {
		String toLog = "###################################\n";
		toLog += "The store has had " + customers + " visits so far\n";
		toLog += "today's sales generated " + (new DecimalFormat("# $,##0.00").format(income)) + " so far\n";
		toLog += "###################################";
		totalRevenueFileLogStream.println(toLog);
		totalRevenueFileLogStream.close();
	}

	

}
