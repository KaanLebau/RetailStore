package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integration.Server.Connection;
import util.exceptions.ServerOfflineException;

class ExternalAccountingTest {
	ExternalAccounting dummy;
	double accountBalance = 1000000; 
	private ByteArrayOutputStream printoutBuffer;
	private PrintStream originalSysOut;

	@BeforeEach
	void setUp() throws Exception {
		dummy = new ExternalAccounting();
		dummy.setAccount(accountBalance);
		
		printoutBuffer = new ByteArrayOutputStream();
		PrintStream inMemSysOut = new PrintStream(printoutBuffer);
		originalSysOut = System.out;
		System.setOut(inMemSysOut);
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy = null;
		
		printoutBuffer = null;
		System.setOut(originalSysOut);
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

	@Test
	void testConnectionEstablished() throws ServerOfflineException {
		try{			
			dummy.connectionControl();
		}catch(ServerOfflineException e) {
			fail("Server online faild");
		}
		
	}

	@Test
	void testConnectionNotEstablished() throws ServerOfflineException {
		dummy.setConnectionStatus(Connection.OFFLINE);
		try {
			dummy.connectionControl();
			fail("connection exception faild");
		} catch(ServerOfflineException e){
        String expectedOutput = "faild";
        assertTrue(e.getMessage().contains(expectedOutput), "connecting ofline server exception faild");
		}
	}
	
	@Test
	void testServerNameInException() throws ServerOfflineException {
		dummy.setConnectionStatus(Connection.OFFLINE);
		try {
			dummy.connectionControl();
			fail("connection exception faild");
		} catch(ServerOfflineException e){
        String expectedOutput = "ACCOUNTING";
        assertTrue(e.getMessage().contains(expectedOutput), "server specifier faild");
		}
	}
}
