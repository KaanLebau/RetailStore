package view;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import model.Sale;
import model.SaleObserver;
import util.Logger;

public class TotalRevenueFileOutput implements SaleObserver, Logger{

	private PrintWriter totalRevenueFileLogStream;
	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatClock = new SimpleDateFormat("HH:mm:ss");

	/**
	 * create a new .txt file 
	 *
	 * 
	 * @throws FileNotFoundException
	 */
	public TotalRevenueFileOutput() throws IOException {

		try {

			totalRevenueFileLogStream = new PrintWriter(new FileWriter(
					"C:\\Users\\ozsan\\OneDrive\\Dokument\\Kth\\Programmering\\java prg\\RetailStorePOS\\log\\TotalRevenueFile.txt"),
					true);
		} catch (IOException e) {

			System.out.println("FileWriter in TotalRevenueFileOutput constructor faild");
		}
	}
	
	@Override
	public void newSale(Sale sale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newLog(String message) {
		totalRevenueFileLogStream.println(message);
	}

}
