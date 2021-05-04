package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExternalAccountingTest {
	ExternalAccounting dummy;
	double accountBalance = 1000000; 

	@BeforeEach
	void setUp() throws Exception {
		dummy = new ExternalAccounting();
		dummy.setAccount(accountBalance);
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy = null;
	}

	@Test
	void testUpdateExternalAccounting() {
		dummy.updateExternalAccounting(500000);
		double expResult = 1500000;
		double result =dummy.getBalance();
		assertEquals(expResult, result, "update external inventory Faild");
	}

	@Test
	void testGetBalance() {
		double expResult = accountBalance;
		double result = dummy.getBalance();
		assertEquals(expResult, result,"get balance Faild");
	}

}
