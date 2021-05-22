package view;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.SaleObserver;
import util.log.LogFactory;

public class TotalRevenueFileOutput implements SaleObserver{
	double balaceSinceTheProgramStarted;
	/**
	 * create a new .txt file 
	 *
	 * §
	 * @throws FileNotFoundException
	 */
	public TotalRevenueFileOutput() {

	}
	
	@Override
	public void newSale(double income, int customers) throws IOException {
		
		try {
			updateBalance(income);
			LogFactory.getLogFactory().getTotalRevenueFileOutput().presentIncome(this.balaceSinceTheProgramStarted, customers);
		} catch (IOException e) {
			System.out.println("in util.log klas TotalRevenueFileOutput method newSale faild");
			throw e;
		}
	}

	private void updateBalance(double income) {
		balaceSinceTheProgramStarted += income;

	}
}
