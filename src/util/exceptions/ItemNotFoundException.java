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

	@Override
	public String getMessage() {
		System.out.println("Wrong item id. tyr again.");
		return super.getMessage();
	}

//	@Override
//	public void printStackTrace() {
//		// TODO Auto-generated method stub
//		super.printStackTrace();
//		System.out.println("halla");
//	}
//	
	
	
}
