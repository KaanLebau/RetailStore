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
import view.View;

/**
 * @author ozsan
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DiscountRegister discountRegister = new DiscountRegister();
		CustomerRegister customerRegister = new CustomerRegister();
		ExternalAccounting externalAccounting = new ExternalAccounting();
		ExternalInventory externalInventory = new ExternalInventory();
		Printer printer = new Printer();
		Controller controller = new Controller(printer, discountRegister, externalInventory, 
				externalAccounting, customerRegister);
		View view = new View(controller);
		
		view.scenario();
	}

}
