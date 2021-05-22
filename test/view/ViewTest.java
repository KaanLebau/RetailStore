package view;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import integration.CustomerRegister;
import integration.DiscountRegister;
import integration.ExternalAccounting;
import integration.ExternalInventory;
import integration.Printer;
import integration.Server.Connection;
import util.exceptions.CustomerDiscountIdNotFoundException;
import util.exceptions.CustomerIdNotFoundException;
import util.exceptions.ItemNotFoundException;
import util.exceptions.ServerOfflineException;

class ViewTest {
	Printer printer;
	DiscountRegister discountRegister;
	ExternalAccounting externalAccounting;
	ExternalInventory externalInventory;
	CustomerRegister customerRegister;
	Controller controller;
	View dummy;
	
	private ByteArrayOutputStream printoutBuffer;
	private PrintStream inMemSysOut;
	private PrintStream originalSysOut;

	@BeforeEach
	void setUp() throws Exception {
		printer = new Printer();
		discountRegister = new DiscountRegister();
		externalInventory = new ExternalInventory();
		externalAccounting = new ExternalAccounting();
		customerRegister = new CustomerRegister();
		controller = new Controller(printer, discountRegister, externalInventory, 
				externalAccounting, customerRegister);
		dummy = new View(controller);
		
		printoutBuffer = new ByteArrayOutputStream();
		inMemSysOut = new PrintStream(printoutBuffer);
		originalSysOut = System.out;
		System.setOut(inMemSysOut);
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy = null;
		controller = null;
		discountRegister = null;
		externalAccounting = null;
		externalInventory = null;
		customerRegister = null;
		printer = null;
		
		
		
		printoutBuffer = null;
		System.setOut(originalSysOut);
	}

	@Test
	void testUpdateViewProductList() throws ServerOfflineException, ItemNotFoundException, CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		dummy.receiptCashTest();
		String output = printoutBuffer.toString();
		String expectedOutput = "Description:";
        assertTrue(output.contains(expectedOutput), "view running total faild");
	}

	@Test
	void testUpdateViewDiscountList() throws ServerOfflineException, ItemNotFoundException, CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		dummy.receiptCashTest();
		String output = printoutBuffer.toString();
		String expectedOutput = "Discounts:";
        assertTrue(output.contains(expectedOutput), "view running total faild");
	}
	
	@Test
	void testUpdateViewRunningTotal() throws ServerOfflineException, ItemNotFoundException, CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		dummy.receiptCashTest();
		String output = printoutBuffer.toString();
		String expectedOutput = "Payment Information:";
        assertTrue(output.contains(expectedOutput), "view running total faild");
	}
	
	@Test
	void testServerOfflineException()  {
		try {
			discountRegister.setConnectionStatus(Connection.OFFLINE);
			dummy.receiptCashTest();
		} catch (Exception e) {
						
			String output = printoutBuffer.toString();
			String expectedOutput = "not establice connection";
			assertTrue(output.contains(expectedOutput), "ServerOfflineException message faild");
		}
		
	}
	
	@Test
	void testCustomerDiscountIdNotFoundException()  {
		try {
			dummy.wrongDiscountId();
		} catch (Exception e) {
						
			String output = printoutBuffer.toString();
			String expectedOutput = "Wrong discount";
			assertTrue(output.contains(expectedOutput), "CustomerDiscountIdNotFoundException message faild");
		}
		
	}
	
	@Test
	void testCustomerIdNotFoundException()  {
		try {
			dummy.wrongCustomerId();
		} catch (Exception e) {
						
			String output = printoutBuffer.toString();
			String expectedOutput = "not registred";
			assertTrue(output.contains(expectedOutput), "CustomerIdNotFoundException message faild");
		}
		
	}
	@Test
	void testItemNotFoundException()  {
		try {
			dummy.wrongCustomerId();
		} catch (Exception e) {
						
			String output = printoutBuffer.toString();
			String expectedOutput = "Wrong item id.";
			assertTrue(output.contains(expectedOutput), "ItemNotFoundException message faild");
		}
		
	}
}
