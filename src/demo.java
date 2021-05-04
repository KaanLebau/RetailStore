
import java.util.List;
import java.util.concurrent.TimeUnit;

import controller.Controller;
import integration.Address;
import integration.CustomerRegister;
import integration.DiscountRegister;
import integration.DiscountTypDTO;
import integration.ExternalAccounting;
import integration.ExternalInventory;
import integration.Printer;
import model.CashRegister;
import model.Discount;
import model.Payment;
import model.Product;
import model.Receipt;
import model.Sale;
import util.Util.Category;
import util.Util.Method;
import view.View;

public class demo {
	public static double FULLPRICE = 1;

	public static void main(String[] args) throws InterruptedException {
		
		
		/*
		Sale aktivSale = new Sale();
		Printer printer = new Printer();
		Adres adres = new Adres("Göteborg", "Andra lång", 666,424);
		CashRegister cR = new CashRegister(adres, printer);
		Product cola = new Product("101","cola", 10, 10, aktivSale);
		Payment payment = new Payment(Method.CASH, 100, aktivSale, cR);
		*/
		
		Printer printer = new Printer();
		DiscountRegister dR= new DiscountRegister();
		ExternalInventory eI = new ExternalInventory();
		ExternalAccounting eA = new ExternalAccounting();
		CustomerRegister cR = new CustomerRegister();
		Controller c = new Controller(printer, dR,eI,eA,cR);
		View v = new View(c);
		
		
		int after =eI.getInventoryList().get(0).getQuantity();
		//c.addProduct("101");
//		c.addProduct("101");
//		c.addProduct("101");
//		c.addProduct("101");
//		c.discountRequest("111", "9999");
		//c.addPayment(Method.CARDTERMINAL);
		
		
		
//		int before =eI.getInventoryList().get(0).getQuantity();
//		System.out.println("inventory befor purchase " + before);
//		System.out.println("inventory befor purchase " + after);
		
		DiscountTypDTO org = new DiscountTypDTO(Category.ITEM, "104", 10, "Campaing product");
		DiscountTypDTO cpy = new DiscountTypDTO(Category.ITEM, "104", 10, "Campaing product");
		boolean same = org.equals(cpy);
		System.out.println(same);
		
	}

	
}
