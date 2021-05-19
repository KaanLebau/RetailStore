package util.exceptions;

public class CustomerIdNotFoundException extends Exception {
	
	/**
	 * throws when customer id is not in the register. 
	 * 
	 * @param failureMessage failure reason
	 */
	public CustomerIdNotFoundException(String failureMessage) {
		super(failureMessage);
	}

}
