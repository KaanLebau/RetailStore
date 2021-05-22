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
import util.exceptions.CustomerDiscountIdNotFoundException;
import util.exceptions.CustomerIdNotFoundException;
import util.exceptions.ItemNotFoundException;
import util.exceptions.ServerOfflineException;

class TotalRevenueViewTest {
	Printer printer;
	DiscountRegister discountRegister;
	ExternalAccounting externalAccounting;
	ExternalInventory externalInventory;
	CustomerRegister customerRegister;
	Controller controller;
	View view;
	
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
		view = new View(controller);
		
		printoutBuffer = new ByteArrayOutputStream();
		inMemSysOut = new PrintStream(printoutBuffer);
		originalSysOut = System.out;
		System.setOut(inMemSysOut);
	}

	@AfterEach
	void tearDown() throws Exception {
		view = null;
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
	void testTotalRevenueView() throws ServerOfflineException, ItemNotFoundException, CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		view.totalRevenuePrint();
		String output = printoutBuffer.toString();
		String expectedOutput = "The store has had 3";
        assertTrue(output.contains(expectedOutput), "change to display faild");
	}

}
