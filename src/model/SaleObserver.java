package model;

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
	 * @param saleInfoDTO information about sale.
	 */
	void newSale(SaleInfoDTO sale);


}
