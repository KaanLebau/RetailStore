package util;

public class CustomerDiscountIdException extends RuntimeException{
	
	/**
	 * throws when customer discount id is not in the register. 
	 * 
	 * @param failureMessage failure reason
	 */
	public CustomerDiscountIdException(String failureMessage) {
		super(failureMessage);
	}

}
