package integration;

import java.util.List;

public interface TransactionObserver  {

	void changeToDisplay(List<Money> changeList);
}
