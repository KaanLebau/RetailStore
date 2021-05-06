package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integration.Address;
import integration.Printer;

class CashRegisterTest {
	private Printer printer;
	private Address adres;
	private CashRegister dummy; 
	
	@BeforeEach
	void setUp() throws Exception {
		printer = new Printer();
		adres = new Address("Göteborg", "Andra lång", 666,424);
		dummy = new CashRegister(adres, printer); 
	}

	@AfterEach
	void tearDown() throws Exception {
		adres = null;
		dummy = null;
	}
	
	@Test
	void testgetBalance() {
		double result = dummy.getBalance();
		double expResult = 6000;
		assertEquals(expResult,result, "get cash register balance Faild");
	}
	@Test
	void testaddToBalance() {
		dummy.addToBalance(500);
		double result = dummy.getBalance();
		double expResult = 6500;
		assertEquals(expResult,result, "get cash register balance Faild");
	}
	
	@Test
	void testCashRegister() {
		boolean result = (dummy instanceof CashRegister);
		assertTrue(result,"dummy is instance of cash register Faild");
	}
	
	@Test
	void testGetCashRegisterId() {
		int expResult = 1;
		int result = dummy.getCashRegisterId();
		assertEquals(expResult,result, "get cash register id Faild");
	}
	
	@Test
	void testGetPrinter() {
		Printer expResult = this.printer;
		Printer result = dummy.getPrinter();
		assertEquals(expResult,result, "get printer Faild");
	}
	

	@Test
	void testGetName() {
		String expResult = "Kth Store";
		String result =  dummy.getStoreName();
		assertEquals(expResult,result, "get store name Faild");
	}

	@Test
	void testGetTelephone() {
		String expResult = "031 666 66 66";
		String result =  dummy.getTelephone();
		assertEquals(expResult,result, "get store telephone Faild");
	}

	@Test
	void testGetCity() {
		String expResult = "Göteborg";
		String result = dummy.getCity();
		assertEquals(expResult, result, "get city Fild");
	}

	@Test
	void testGetStreet() {
		String expResult = "Andra lång";
		String result =  dummy.getStreet();
		assertEquals(expResult, result,"get street Fild");
	}

	@Test
	void testGetBuildingsNumber() {
		int expResult = 666;
		int result =  dummy.getBuildingsNumber();
		assertEquals(expResult, result, "get building number Faild");
	}

	@Test
	void testGetZipCode() {
		int expResult = 424;
		int result =  dummy.getZipCode();
		assertEquals(expResult, result, "get zip code Faild");
	}

}
