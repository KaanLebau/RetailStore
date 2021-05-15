package view;

import model.SaleInfoDTO;
import model.SaleObserver;
import util.log.Logger;

public class TotalRevenueView implements SaleObserver, Logger {
	private double totalSum = 0;
	private SaleInfoDTO saleInfoDTO;
	
	@Override
	public void newSale(SaleInfoDTO saleInfoDTO) {
		this.saleInfoDTO = saleInfoDTO;
	}
	
	public String toDisplay() {
		String info = "To";
		return info;
	}
	

	@Override
	public void newLog(String message) {
		System.out.println(message);
		
	}

}
