package integration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integration.Server.Connection;
import util.CustomerDiscountIdException;
import util.CustomerRegistryException;
import util.ServerOfflineException;
import util.Util.Category;

class DiscountRegisterTest {
	Date toDay = new Date();
	DiscountRegister dummy;
	List<DiscountDTO> dummyList;
	DiscountDTO singelItemDiscount;
	DiscountDTO quantityDiscount;
	DiscountDTO customerDiscount;
	boolean customer = true;
	private ByteArrayOutputStream printoutBuffer;
	private PrintStream originalSysOut;

	@BeforeEach
	void setUp() throws Exception {
		toDay = new Date();
		dummy = new DiscountRegister();
		dummyList = new ArrayList<>();
		singelItemDiscount = new DiscountDTO(Category.ITEM, "104", 10, "Campaing product");
		quantityDiscount = new DiscountDTO(Category.QUANTITY, "101", 3, 11, "Buy 3 pay for 2");
		customerDiscount = new DiscountDTO(Category.CUSTOMER, "111", 12, "12% discount ");
		
		printoutBuffer = new ByteArrayOutputStream();
		PrintStream inMemSysOut = new PrintStream(printoutBuffer);
		originalSysOut = System.out;
		System.setOut(inMemSysOut);
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy = null;
		dummyList = null;
		singelItemDiscount = null;
		quantityDiscount = null;
		customerDiscount = null;
		
		printoutBuffer = null;
		System.setOut(originalSysOut);
		
	}

	
	
	@Test
	void testUpdateList() {
		dummy.updateList(dummyList);
		assertEquals(dummyList, dummy.getList(),"update discount resgister faild");
		
	}
	
	@Test
	void testSearchCustomerDiscount() throws CustomerRegistryException, CustomerDiscountIdException {
		DiscountDTO expResult =customerDiscount;
		DiscountDTO resultat = dummy.searchCustomerDiscount("111", customer); 
		assertEquals(expResult,resultat,"Search customer discount fails");
		
	}
	@Test
	void testSearchCustomerDiscountNoCustom() throws CustomerRegistryException, CustomerDiscountIdException {
		
		try {
			customer = false;
			dummy.searchCustomerDiscount("123", customer);
			fail("thowing discount registery exception faild");
		}catch(CustomerDiscountIdException e){
			String expResult = "quantityCheck";
			assertTrue(e.getMessage().contains(expResult),"exception throws wrong message");
		}
		
	}
	
	@Test
	void testSearchCustomerDiscountNotExist() throws CustomerRegistryException, CustomerDiscountIdException {
		
		try {
			dummy.searchCustomerDiscount("487", customer);
			fail("thowing customer discount id exception faild");
		}catch(CustomerDiscountIdException e){
			String expResult = "quantityCheck";
			assertTrue(e.getMessage().contains(expResult),"exception throws wrong message");
			
		}
		
		
		
		
	}
	@Test
	void testSearchItemDiscount() {
		DiscountDTO expResult =singelItemDiscount;
		DiscountDTO result =dummy.searchItemDiscount("104", 1);
		assertEquals(expResult,result,"search item discount category ITEM Faild");
		expResult =quantityDiscount;
		result = dummy.searchItemDiscount("101", 3);
		assertEquals(expResult,result,"search item discount category QUANTOTY Faild");
	}
	
	@Test
	void testSearchItemDiscountNotExist() {
		DiscountDTO expResult = new DiscountDTO();
		DiscountDTO result = dummy.searchItemDiscount("666", 3);
		assertEquals(expResult.getCategory(),result.getCategory(),
				"search item discount with invalid Discount default category Faild");
		assertEquals(expResult.getDiscountAmount(),result.getDiscountAmount(),
				"search item discount with invalid Discount default discount amount Faild");
		assertEquals(expResult.getDiscountId(),result.getDiscountId(),
				"search item discount with invalid Discount default discount id Faild");
		assertEquals(expResult.getDiscountPercent(),result.getDiscountPercent(),
				"search item discount with invalid Discount default discount percent Faild");
		assertEquals(expResult.getItemId(),result.getItemId(),
				"search item discount with invalid Discount default item id Faild");
		assertEquals(expResult.getItemQuantity(),result.getItemQuantity(),
				"search item discount withinvalid Discount default quantity Faild");
	}

	@Test
	void testEqualTrue() {
		DiscountDTO result = singelItemDiscount;
		assertTrue(singelItemDiscount.equals(result),"discount equal Faild");
	}

	@Test
	void testEqualFlaseWrongItemId() {
		DiscountDTO result = new DiscountDTO(Category.ITEM, "212121", 15, "Item discount");
		DiscountDTO expResult = singelItemDiscount;
		assertFalse(expResult.equals(result),"discount equal Faild");
	}
	
	@Test
	void testEqualFlaseWrongCategory() {
		DiscountDTO result = new DiscountDTO(Category.QUANTITY, "232323", 3, 15, "Item discount");
		DiscountDTO expResult = singelItemDiscount;
		assertFalse(expResult.equals(result),"discount equal Faild");
	}
	
	@Test
	void testEqualFlaseWrongDiscountId() {
		DiscountDTO result = new DiscountDTO(Category.CUSTOMER, "131313", 10, "Customer discount");
		DiscountDTO expResult = customerDiscount;
		assertFalse(expResult.equals(result),"discount equal Faild");
	}
	
	@Test
	void testConnectionEstablished() throws ServerOfflineException {
		try {			
			dummy.connectionControl();
		}catch(ServerOfflineException e ) {
			fail("server is online faild");
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
        String expectedOutput = "DISCOUNT";
        assertTrue(e.getMessage().contains(expectedOutput), "server specifier faild");
		}
	}
	
}
