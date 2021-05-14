package view;

import model.Sale;
import model.SaleObserver;
import util.Logger;

public class TotalRevenueView implements SaleObserver, Logger {

	@Override
	public void newSale(Sale sale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newLog(String message) {
		System.out.println(message);
		
	}

}
