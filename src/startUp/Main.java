/**
 * 
 */
package startUp;

import controller.Controller;
import integration.CustomerRegister;
import integration.DiscountRegister;
import integration.ExternalAccounting;
import integration.ExternalInventory;
import integration.Printer;
import integration.Server.Connection;
import util.exceptions.CustomerDiscountIdException;
import util.exceptions.CustomerRegistryException;
import util.exceptions.ItemNotFoundException;
import util.exceptions.ServerOfflineException;
import view.View;

/**
 * @author ozsan
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) 
			throws Exception {
		DiscountRegister discountRegister = new DiscountRegister();
		CustomerRegister customerRegister = new CustomerRegister();
		ExternalAccounting externalAccounting = new ExternalAccounting();
		ExternalInventory externalInventory = new ExternalInventory();
		Printer printer = new Printer();
		Controller controller = new Controller(printer, discountRegister, externalInventory, 
				externalAccounting, customerRegister);
		View view = new View(controller);
		
		
		//discountRegister.setConnectionStatus(Connection.OFFLINE);
		//customerRegister.setConnectionStatus(Connection.OFFLINE);
		//externalInventory.setConnectionStatus(Connection.OFFLINE);
		//externalAccounting.setConnectionStatus(Connection.OFFLINE);
		
		
		
		//controller.addProduct("10");
		
		view.scenario1a();
		
		//view.threeSale();
		
		
		
		
		
		
		
		
	}

}
