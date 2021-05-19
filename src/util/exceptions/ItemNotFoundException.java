package util.exceptions;

public class ItemNotFoundException extends Exception{
	
	/**
	 * thrown when an item could not be found in externalInventory
	 * 
	 * @param failureMessage information about what went wrong 	
	 */
	public ItemNotFoundException(String failureMessage) {
		super(failureMessage);
	}

	
	
	
}
