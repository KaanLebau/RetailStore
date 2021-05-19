package util.log;

import java.util.List;

import integration.Money;

public class MoneyRunningLowLog{

	
	public void moneyWarning(List<Money> moneyList) {
		
		for(Money money : moneyList) {
			String info = money.getValue() + " ";
			info += money.getCurrency() + " ";
			info += "is runing low\n";
			System.out.println(info);			
		}
			
		
	}

}
