package integration;

import java.util.ArrayList;
import java.util.List;

import integration.Server.Connection;
import integration.Server.ServerTyp;
import util.exceptions.CustomerIdNotFoundException;
import util.exceptions.ServerOfflineException;

public class CustomerRegister {
	
	private List<CustomerDTO> customerRegister = new ArrayList<>();
	private Connection connection;
	private ServerTyp serverTyp;
	
	
	public CustomerRegister() {
		this.connection = Connection.ONLINE;
		this.serverTyp = ServerTyp.CUSTOMER;
		CustomerDTO kaan = new CustomerDTO("9999", "Kaan");
		CustomerDTO lina = new CustomerDTO("8888", "Lina");
		CustomerDTO emmylou = new CustomerDTO("7777", "Emmylou");
		CustomerDTO anakin = new CustomerDTO("6666", "anakin");
		customerRegister.add(emmylou);
		customerRegister.add(anakin);
		customerRegister.add(lina);
		customerRegister.add(kaan);

	}
		
	/**
	 * updates customer register with new register
	 * @param register customer list
	 */
	public void updateRegister(List<CustomerDTO> register) {
		this.customerRegister = register;
	}
	/**
	 * gets customer list
	 * @return customerReister
	 */
	public List<CustomerDTO> getList() {
		List<CustomerDTO> listToSend = this.customerRegister;
		return listToSend;
	}
	/**
	 * gets current server status
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
	 * CustomerId used for check every customerId in customerRegister
	 * throw an uncheck exception
	 * 
	 * @param customerId used for identification
	 * @return true / false
	 * @throws CustomerIdNotFoundException when customer id is not found 
	 */
	public boolean searchCustomerDTO(String customerId) 
			throws CustomerIdNotFoundException {
		for(CustomerDTO search: customerRegister)
			if(search.getId().equalsIgnoreCase(customerId)) {				
				return true;
			}
		throw new CustomerIdNotFoundException("throw from CustomerRegister class, "
				+ "searchCustomerDTO metod customer id : " + customerId);
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
