package integration;

import integration.Server.Connection;
import integration.Server.ServerTyp;
import util.*;
import util.exceptions.ServerOfflineException;



public class ExternalAccounting {
	//TODO
//	private static ExternalAccounting externalAccounting;
//	
	private double account;
	Connection connection;
	ServerTyp serverTyp;
//	
//	public static ExternalAccounting getInstanceOfExternalAccounting() {
//		if (externalAccounting == null)
//			externalAccounting = new ExternalAccounting();
//		
//		return externalAccounting; 
//			
//	}
	
	
	public ExternalAccounting() {
		this.connection = Connection.ONLINE;
		this.serverTyp = ServerTyp.ACCOUNTING;
	}
	/**
	 * set amount in this account
	 * @param in amount money
	 */
	public void setAccount(double in) {
		this.account = in;
	}
	/**
	 * Updates account after end sale
	 * @param money
	 */
	public void updateExternalAccounting(double money) {
		account += money;
	}
	/**
	 * gets balance in account 
	 * @return
	 */
	public double getBalance() {
		return  this.account;
	}

	/**
	 * server status
	 * 
	 * @return status
	 */
	public Connection getConnectionStatus() {
		return this.connection;
	}
	/**
	 * set server status
	 * 
	 * @param connection status
	 */
	public void setConnectionStatus(Connection connection) {
		this.connection = connection;
	}
	/**
	 * controls server connection
	 * throw check exception
	 * 
	 * @throws ServerOfflineException when there is not connettion to the server
	 */
	public void connectionControl() throws ServerOfflineException {
		Server.connectionCheck(this.serverTyp, this.connection);
	}

}
