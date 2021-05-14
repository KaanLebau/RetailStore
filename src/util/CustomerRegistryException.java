package util;

public class CustomerRegistryException extends RuntimeException {
	
	/**
	 * throws when customer id is not in the register. 
	 * 
	 * @param failureMessage failure reason
	 */
	public CustomerRegistryException(String failureMessage) {
		super(failureMessage);
	}

}
