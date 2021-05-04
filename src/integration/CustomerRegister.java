package integration;

import java.util.ArrayList;
import java.util.List;

public class CustomerRegister {
	
	private List<CustomerDTO> customerRegister = new ArrayList<>();
	
	public CustomerRegister() {
		CustomerDTO kaan = new CustomerDTO("9999", "Kaan");
		CustomerDTO lina = new CustomerDTO("8888", "Lina");
		CustomerDTO emmylou = new CustomerDTO("7777", "Emmylou");
		CustomerDTO anakin = new CustomerDTO("6666", "anakin");
		customerRegister.add(emmylou);
		customerRegister.add(anakin);
		customerRegister.add(lina);
		customerRegister.add(kaan);
	}
		
	public void updateRegister(List<CustomerDTO> register) {
		this.customerRegister = register;
	}
	public List<CustomerDTO> getList() {
		List<CustomerDTO> listToSend = this.customerRegister;
		return listToSend;
	}
	
	/**
	 * CustomerId used for check every customerId in customerRegister  
	 * @param customerId used for identification
	 * @return true/false
	 */
	public boolean searchCustomerDTO(String customerId) {		
		boolean foundCustomer = false;
		for(CustomerDTO search: customerRegister)
			if(search.getId().equalsIgnoreCase(customerId)) {				
				foundCustomer = true;
				break;
			}
		
		return foundCustomer;
	}

}
