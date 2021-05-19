package view;

import java.util.List;

import integration.Money;
import integration.TransactionObserver;
import util.log.LogFactory;

public class ChangeAmount implements TransactionObserver {
	

	@Override
	public void changeToDisplay(List<Money> changeList) {
		
		LogFactory.getLogFactory().getChangeAmountLog().changeAmountToDisplay(changeList);
	}

}
