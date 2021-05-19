package view;

import java.util.List;

import integration.Money;
import integration.MoneyObserver;
import util.log.LogFactory;

public class MoneyRunningLow implements MoneyObserver {
	@Override
	public void moneyWarning(List<Money> monelList) {
		LogFactory.getLogFactory().getMoneyRunningLowLog().moneyWarning(monelList);
		
	}

}
