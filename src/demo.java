
import controller.Controller;
import integration.CustomerRegister;
import integration.DiscountRegister;
import integration.ExternalAccounting;
import integration.ExternalInventory;
import integration.Printer;
import view.View;

public class demo {
	public static double FULLPRICE = 1;

	public static void main(String[] args) throws InterruptedException {
		
		

		
		
		Printer printer = new Printer();
		DiscountRegister dR= new DiscountRegister();
		ExternalInventory eI = new ExternalInventory();
		ExternalAccounting eA = new ExternalAccounting();
		CustomerRegister cR = new CustomerRegister();
		Controller c = new Controller(printer, dR,eI,eA,cR);
		View v = new View(c);
		
		

		v.scenario2();
		
	}

	
}
