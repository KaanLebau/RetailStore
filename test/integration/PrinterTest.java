package integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import model.Sale;
import util.exceptions.CustomerDiscountIdNotFoundException;
import util.exceptions.CustomerIdNotFoundException;
import util.exceptions.ItemNotFoundException;
import util.exceptions.ServerOfflineException;
import view.View;

class PrinterTest {
	
	Printer dummy;
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
		dummy = new Printer();
		discountRegister = new DiscountRegister();
		externalInventory = new ExternalInventory();
		externalAccounting = new ExternalAccounting();
		customerRegister = new CustomerRegister();
		controller = new Controller(dummy, discountRegister, externalInventory, 
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
		dummy = null;
		
		
		
		printoutBuffer = null;
		System.setOut(originalSysOut);
	}

	@Test
	void testPrintOutStoreInformation() throws ServerOfflineException, ItemNotFoundException, CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		view.receiptCashTest();
		dummy.printOut(controller.getPayment().getReceipt());
		String output = printoutBuffer.toString();
		String expectedOutput = "Kth";
        assertTrue(output.contains(expectedOutput), "print store information faild");
	}
	
	@Test
	void testPrintOutProductList() throws ServerOfflineException, ItemNotFoundException, CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		view.receiptCashTest();
		dummy.printOut(controller.getPayment().getReceipt());
		String output = printoutBuffer.toString();
		String expectedOutput = "Cola";
        assertTrue(output.contains(expectedOutput), "print product list faild");

	}
	
	@Test
	void testPrintOutDiscountList() throws ServerOfflineException, ItemNotFoundException, CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		view.receiptCashTest();
		dummy.printOut(controller.getPayment().getReceipt());
		String output = printoutBuffer.toString();
		String expectedOutput = "Loyality Discount";
        assertTrue(output.contains(expectedOutput), "print discount list faild");
	}
	
	@Test
	void testPrintOutPaymentInformation() throws ServerOfflineException, ItemNotFoundException, CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		view.receiptCashTest();
		dummy.printOut(controller.getPayment().getReceipt());
		String output = printoutBuffer.toString();
		String expectedOutput = "Payment Information:";
        assertTrue(output.contains(expectedOutput), "print payment information faild");
	}
	
	@Test
	void testPrintOutPaymentMetodCASH() throws ServerOfflineException, ItemNotFoundException, CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		view.receiptCashTest();
		dummy.printOut(controller.getPayment().getReceipt());
		String output = printoutBuffer.toString();
		String expectedOutput = "CASH";
        assertTrue(output.contains(expectedOutput), "print product list faild");
		
	}
	@Test
	void testPrintOutPaymentMetodCARDTERMINAL() throws ServerOfflineException, ItemNotFoundException, CustomerDiscountIdNotFoundException, CustomerIdNotFoundException, Exception {
		view.receiptCARDTERMINALTest();
		dummy.printOut(controller.getPayment().getReceipt());
		String output = printoutBuffer.toString();
		String expectedOutput = "CARDTERMINAL";
        assertTrue(output.contains(expectedOutput), "print product list faild");
		
	}
}
