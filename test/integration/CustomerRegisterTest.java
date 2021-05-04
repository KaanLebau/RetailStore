package integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerRegisterTest {

	CustomerRegister dummyRegister;
	List<CustomerDTO> dummyList;
	CustomerDTO customer1 = new CustomerDTO("123", "Kalle");
	CustomerDTO customer2 = new CustomerDTO("456", "Janne");

	
	@BeforeEach
	public void setup() {
		dummyRegister = new CustomerRegister();
		dummyList = new ArrayList<>();
		dummyList.add(customer1);
		dummyList.add(customer2);
	}
	@AfterEach
	public void reset() {
		dummyRegister = null;
		dummyList = null;
	}
	
	@Test
	void testCustomerRegister() {
	assertNotNull(dummyRegister, "customer register contructor faild");	
	}

	@Test
	void testNewCustomerRegisterList() {
		dummyRegister.updateRegister(dummyList);
		assertEquals(dummyList, dummyRegister.getList(),"New customer register list Faild");
		
	}

	@Test
	void testGetList() {
		
		List<CustomerDTO>list = dummyRegister.getList();
		assertEquals(list, dummyRegister.getList(),"get customer list faild");
	}

	@Test
	void testSearchCustomerDTO() {
		
		boolean found = dummyRegister.searchCustomerDTO("9999");
		assertTrue(found,"search existing customer faild");
		boolean notFound = dummyRegister.searchCustomerDTO("111");
		assertFalse(notFound,"search not existing customer faild");
	}

}
