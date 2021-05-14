package util;

/**
 * The Exception, handles if or when an items quantity become below zero
 * @author ozsan
 *
 */
public class ItemQuantityInInventoryIsIncorrect extends RuntimeException{

	
	/**
	 * throws when item quantity below zero
	 * 
	 * @param failureMessage failure reason
	 */
	public ItemQuantityInInventoryIsIncorrect(String failureMessage) {
		super(failureMessage);
	}

}
