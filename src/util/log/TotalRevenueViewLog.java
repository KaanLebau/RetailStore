package util.log;

import java.text.DecimalFormat;
/**
 * prints total income since program started 
 * on standard output 
 * @author ozsan
 *
 */
public class TotalRevenueViewLog {

	public TotalRevenueViewLog() {

	}

	public void presentIncome(double income, int customers) {
		String info = "###################################\n";
		info += "The store has had " + customers + " visits so far\n";
		info += "today's sales generated " + (new DecimalFormat("# $,##0.00").format(income)) + ".\n";
		info += "###################################";
		System.out.println(info);
	}

}
