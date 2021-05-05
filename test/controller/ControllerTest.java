package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import integration.CustomerRegister;
import integration.DiscountRegister;
import integration.ExternalAccounting;
import integration.ExternalInventory;
import integration.Printer;
import model.Product;
import model.Sale;
import util.Util.Method;

class ControllerTest {
	Printer printer;
	DiscountRegister discountRegister; 
	ExternalAccounting externalAccounting;
	ExternalInventory externalInventory;
	CustomerRegister customerRegister;
	Controller dummy; 
	Sale sale;

	@BeforeEach
	void setUp() throws Exception {
		printer= new Printer();
		discountRegister = new DiscountRegister();
		externalAccounting = new ExternalAccounting();
		externalInventory = new ExternalInventory();
		customerRegister = new CustomerRegister();
		dummy = new Controller(printer, discountRegister, externalInventory,
				externalAccounting, customerRegister); 
		sale = new Sale();
	}

	@AfterEach
	void tearDown() throws Exception {
		printer = null;
		discountRegister = null;
		externalAccounting = null;
		externalInventory = null;
		customerRegister = null;
		dummy = null;
		sale = null;
	}

	@Test
	void testAddSingelProduct() {
		dummy.addProduct("101");
		Product result = dummy.getSaleInfoDTO().getProductsInSale().get(0);
		Product expResult = new Product("101", "Cola", 10, 10,1);
		assertEquals(expResult, result,"add singel product to the sale Faild");
		
	}

	@Test
	void testAddSeveralProducts() {
		dummy.addProduct("101", 4);
		int expResult = 4;
		int result = dummy.getSaleInfoDTO().getProductsInSale().get(0).getQuantity();
		assertEquals(expResult, result,"add several product to the sale Faild");
	}
	
	@Test
	void testAddThreeProductsAndGetDiscount() {
		dummy.addProduct("101");
		dummy.addProduct("101");
		dummy.addProduct("101");
		double expResult = 22;
		double result = dummy.getSaleInfoDTO().getRuningTotal();
		assertEquals(expResult,result, "Wrong amount to pay");
	}
	
	@Test
	void testAddCampainProduct() {
		Product product = new Product("104", "Ost", 60, 10,1);
		assertEquals(66, product.grossPrice());
		dummy.addProduct("104");
		double expResult = 56;
		double result = dummy.getSaleInfoDTO().getRuningTotal();
		assertEquals(expResult,result,"item discount not applied wrong amount to pay");
	}
	
	
	@Test
	void testCustomerDiscountRequest() {
		dummy.addProduct("101");
		dummy.addProduct("101");
		dummy.discountRequest("111", "9999");
		double expResult = 16.5;
		double result = dummy.getSaleInfoDTO().getRuningTotal();
		assertEquals(expResult,result, "customer discount not applied Wrong amount to pay");
	}
	
	@Test
	void testAddPaymentMethodCardTerminalPayment() {
		dummy.addProduct("101");
		dummy.addPayment(Method.CARDTERMINAL);
		assertTrue(dummy.getPayment().getPaymentDone(), "Payment done");
	}

	@Test
	void testAddPaymentMethodCashPayment() {
		dummy.addProduct("101");
		dummy.addPayment(Method.CASH, 50);
		assertTrue(dummy.getPayment().getPaymentDone(), "Payment done");
	}
	
}
