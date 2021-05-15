package util.exceptions;

/**
 * The Exception, handles if or when an items quantity become below zero
 * @author ozsan
 *
 */
public class ItemQuantityInInventoryIsIncorrectException extends RuntimeException{

	
	/**
	 * throws when item quantity below zero
	 * 
	 * @param failureMessage failure reason
	 */
	public ItemQuantityInInventoryIsIncorrectException(String failureMessage) {
		super(failureMessage);
	}

}
