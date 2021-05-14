package integration;

import util.ServerOfflineException;

public class Server {
	
	/**
	 * Possibility's for server status
	 * 
	 * @author ozsan
	 *
	 */
	public enum Connection{
		ONLINE, OFFLINE
	}
	/**
	 * existing servers in program
	 * 
	 * @author ozsan
	 *
	 */
	public enum ServerTyp {
		ACCOUNTING, INVENTORY, CUSTOMER, DISCOUNT;

	}
	/**
	 * controls if connection with server made or not
	 * 
	 * @param serverTyp to get which server failed
	 * @param connection server status
	 * @throws ServerOfflineException when an server is offline 
	 */
	public static void connectionCheck(ServerTyp serverTyp, Connection connection) throws ServerOfflineException {
			
			
		String statusMessage = "Connection with ";
		if (connection == Connection.OFFLINE) {
			switch (serverTyp) {
			case ACCOUNTING:
				statusMessage += "ACCOUNTING server faild";
				throw new ServerOfflineException(statusMessage);
			case INVENTORY:
				statusMessage += "INVENTORY server faild";
				throw new ServerOfflineException(statusMessage);
			case CUSTOMER:
				statusMessage += "CUSTOMER server faild";
				throw new ServerOfflineException(statusMessage);
			case DISCOUNT:
				statusMessage += "DISCOUNT server faild";
				throw new ServerOfflineException(statusMessage);
			default:
				break;
			}
		}

	}
	
}
