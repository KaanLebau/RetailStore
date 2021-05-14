package util;
/**
 * this interface logs message.
 * Classes uses log implements this interface.
 * 
 * @author ozsan
 *
 */
public interface Logger {

	/**
	 * The specified message is printed to the log
	 * 
	 * @param message the message that will be logged.
	 */
	void newLog(String message);
}
