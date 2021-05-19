package integration;

import java.util.List;

public interface Transaction {

	
	List<Money> moneyFlow(double paid, double change);
}
