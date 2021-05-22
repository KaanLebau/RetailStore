package model;

import java.io.IOException;

/**
 *  A listener interface for receiving notifications 
 *  about an sale has successfully complied.
 *  Classes uses this notifications implements this interface.
 *  
 * @author ozsan
 *
 */
public interface SaleObserver {
	
	/**
	 * Notified when a sale successfully complied.
	 * 
	 * @param saleInfoDTO information about complied sale.
	 * @throws IOException 
	 */
	void newSale(double totalToPay, int customers) throws IOException;


}
