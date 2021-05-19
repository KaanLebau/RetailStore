package util.log;

import java.util.List;

import integration.Money;

public class ChangeAmountLog {
	
	
	ChangeAmountLog(){
	}
	
	public void changeAmountToDisplay(List<Money> changeList) {		
		StringBuilder change = new StringBuilder();
		change.append("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n");
		
		change.append("- Banknote   currency   quantity  -\n");
		for (Money money : changeList) {
			change.append("-  " + money.getValue() + "\t\t");
			change.append(money.getCurrency() + "   \t");
			change.append("   " + money.getQuantityInChange() + "      -\n");
		}
		change.append("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-\n");
		System.out.println(change.toString());
	}
}
