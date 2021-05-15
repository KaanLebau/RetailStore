package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integration.Server.Connection;
import util.exceptions.CustomerRegistryException;
import util.exceptions.ServerOfflineException;

class CustomerRegisterTest {

	CustomerRegister dummyRegister;
	List<CustomerDTO> dummyList;
	CustomerDTO customer1 = new CustomerDTO("123", "Kalle");
	CustomerDTO customer2 = new CustomerDTO("456", "Janne");
	private ByteArrayOutputStream printoutBuffer;
	private PrintStream originalSysOut;

	@BeforeEach
	public void setup() {
		dummyRegister = new CustomerRegister();
		dummyList = new ArrayList<>();
		dummyList.add(customer1);
		dummyList.add(customer2);

		printoutBuffer = new ByteArrayOutputStream();
		PrintStream inMemSysOut = new PrintStream(printoutBuffer);
		originalSysOut = System.out;
		System.setOut(inMemSysOut);
	}

	@AfterEach
	public void reset() {
		dummyRegister = null;
		dummyList = null;

		printoutBuffer = null;
		System.setOut(originalSysOut);
	}

	@Test
	void testCustomerRegister() {
		assertNotNull(dummyRegister, "customer register contructor faild");
	}

	@Test
	void testNewCustomerRegisterList() {
		dummyRegister.updateRegister(dummyList);
		assertEquals(dummyList, dummyRegister.getList(), "New customer register list Faild");

	}

	@Test
	void testGetList() {

		List<CustomerDTO> list = dummyRegister.getList();
		assertEquals(list, dummyRegister.getList(), "get customer list faild");
	}

	@Test
	void testSearchCustomerDTO() {
		boolean found = dummyRegister.searchCustomerDTO("9999");
		assertTrue(found, "search existing customer faild");
	}

	@Test
	void testSearchCustomerDTOWithWrogId() throws CustomerRegistryException{
		try {
			dummyRegister.searchCustomerDTO("111");
			fail("search non existing customer excetption faild");
		}catch (CustomerRegistryException e) {
			String expResult = "searchCustomerDTO";
			assertTrue(e.getMessage().contains(expResult));
		}
	}

	@Test
	void testConnectionEstablished() throws ServerOfflineException {
		try {

			dummyRegister.connectionControl();
		} catch (ServerOfflineException e) {
			fail("Server online faild");
		}

	}

	@Test
	void testConnectionNotEstablished() throws ServerOfflineException {
		dummyRegister.setConnectionStatus(Connection.OFFLINE);
		try {
			dummyRegister.connectionControl();
			fail("connection exception faild");
		} catch (ServerOfflineException e) {
			String expectedOutput = "faild";
			String expectedServer = "CUSTOMER";
			assertTrue(e.getMessage().contains(expectedOutput), "connecting ofline server exception faild");
			assertTrue(e.getMessage().contains(expectedServer), "server specifier faild");
		}
	}
}
