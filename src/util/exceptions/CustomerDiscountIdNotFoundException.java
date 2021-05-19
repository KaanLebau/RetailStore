package util.exceptions;

public class CustomerDiscountIdNotFoundException extends Exception{
	
	/**
	 * throws when customer discount id is not in the register. 
	 * 
	 * @param failureMessage failure reason
	 */
	public CustomerDiscountIdNotFoundException(String failureMessage) {
		super(failureMessage);
	}

}
