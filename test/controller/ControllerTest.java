package controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integration.CustomerRegister;
import integration.DiscountRegister;
import integration.ExternalAccounting;
import integration.ExternalInventory;
import integration.ItemDTO;
import integration.Printer;
import model.Product;
import model.Sale;
import util.enums.Method;
import util.exceptions.CustomerDiscountIdNotFoundException;
import util.exceptions.CustomerIdNotFoundException;
import util.exceptions.ItemNotFoundException;
import util.exceptions.ItemQuantityInInventoryIsIncorrectException;
import util.exceptions.ServerOfflineException;

class ControllerTest {
	Printer printer;
	DiscountRegister discountRegister;
	ExternalAccounting externalAccounting = new ExternalAccounting();
	ExternalInventory externalInventory;
	CustomerRegister customerRegister;
	Controller dummy;
	Sale sale;
	double balance;

	@BeforeEach
	void setUp() throws Exception {
		printer = new Printer();
		discountRegister = new DiscountRegister();
		balance = externalAccounting.getBalance();
		externalInventory = new ExternalInventory();
		//externalAccounting = new ExternalAccounting();
		customerRegister = new CustomerRegister();
		dummy = new Controller(printer, discountRegister, externalInventory, 
				externalAccounting, customerRegister);
		dummy.cerateNewSale();
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
	void testNewSale() {
		dummy.cerateNewSale();
		int result = dummy.getSaleInfoDTO().getSaleId();
		int expResult = 2;
		assertEquals(expResult, result, "create new sale faild");

	}

	@Test
	void testAddSingelProduct() throws ServerOfflineException, ItemNotFoundException, Exception {
		dummy.cerateNewSale();
		dummy.addProduct("101");
		Product result = dummy.getSaleInfoDTO().getProductsInSale().get(0);
		Product expResult = new Product("101", "Cola", 10, 10, 1);
		assertEquals(expResult, result, "add singel product to the sale Faild");

	}

	@Test
	void testAddSeveralProducts()
			throws ItemNotFoundException, ItemQuantityInInventoryIsIncorrectException, ServerOfflineException, Exception {
		dummy.cerateNewSale();
		dummy.addProduct("101", 4);
		int expResult = 4;
		int result = dummy.getSaleInfoDTO().getProductsInSale().get(0).getQuantity();
		assertEquals(expResult, result, "add several product to the sale Faild");
	}

	@Test
	void testAddThreeProductsAndGetDiscount() throws ServerOfflineException, ItemNotFoundException, Exception {
		dummy.cerateNewSale();
		dummy.addProduct("101");
		dummy.addProduct("101");
		dummy.addProduct("101");
		double expResult = 22;
		double result = dummy.getSaleInfoDTO().getRuningTotal();
		assertEquals(expResult, result, "Wrong amount to pay faild");
	}

	@Test
	void testAddCampainProduct() throws ServerOfflineException, ItemNotFoundException, Exception {
		ItemDTO item = externalInventory.getItemList().get(4);
		double grossPrice = item.getNetPrice() * (1 + (item.getVAT() / 100));
		assertEquals(66, grossPrice, "orginal price Faild");
		dummy.addProduct("104");
		double expResult = 56;
		double result = dummy.getSaleInfoDTO().getRuningTotal();
		assertEquals(expResult, result, "item discount not applied wrong amount to pay faild");
	}

	@Test
	void testCustomerDiscountRequest() throws ServerOfflineException, ItemNotFoundException, CustomerIdNotFoundException,
			CustomerDiscountIdNotFoundException, Exception {
		dummy.addProduct("101");
		dummy.addProduct("101");
		dummy.discountRequest("111", "9999");
		double expResult = 16.5;
		double result = dummy.getSaleInfoDTO().getRuningTotal();
		assertEquals(expResult, result, "customer discount not applied Wrong amount to pay faild");
	}

	@Test
	void testCustomerDiscountRequestWrongDiscountId() throws ServerOfflineException, ItemNotFoundException,
			CustomerIdNotFoundException, CustomerDiscountIdNotFoundException,Exception {
		try {
			dummy.addProduct("101");
			dummy.addProduct("101");
			dummy.discountRequest("222", "9999");
			fail("customer discount id exception faild");
		}catch(CustomerDiscountIdNotFoundException e) {
			assertTrue(e.getMessage().contains("Catched"),"CustomerDiscountIdException wrong msg");
		}
	}

	@Test
	void testCustomerDiscountRequestWrongCustomerId() throws ServerOfflineException, ItemNotFoundException,
			CustomerIdNotFoundException, CustomerDiscountIdNotFoundException,Exception {
		try {
			dummy.addProduct("101");
			dummy.addProduct("101");
			dummy.discountRequest("111", "99");
			fail("CustomerRegistryException faild");
		}catch(CustomerIdNotFoundException e) {
		assertTrue(e.getMessage().contains("Catched"),"CustomerRegistryException wrong msg");	
		}
		
	}

	@Test
	void testCustomerDiscountRequestWrongCustomerIdAndDiscountId() throws ServerOfflineException, ItemNotFoundException,
			CustomerIdNotFoundException, CustomerDiscountIdNotFoundException,Exception {
		try {
			dummy.addProduct("101");
			dummy.addProduct("101");
			dummy.discountRequest("22", "999");
			fail("customer discount id exception faild");
		}catch(CustomerIdNotFoundException e) {
			assertTrue(e.getMessage().contains("Catched"),"CustomerRegistryException wrong msg");
		}
	}

	@Test
	void testAddPaymentMethodCardTerminalPayment() throws ServerOfflineException, ItemNotFoundException,Exception {
		dummy.addProduct("101");
		dummy.addPayment(Method.CARDTERMINAL);
		assertTrue(dummy.getPayment().getPaymentDone(), "Payment method CARDTERMINAL faild");
	}

	@Test
	void testAddPaymentMethodCashPayment() {
		try {
			dummy.addProduct("101");
			dummy.addPayment(Method.CASH, 50);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(dummy.getPayment().getPaymentDone(), "Payment method CASH faild");
	}

	@Test
	void testExternalAccountingUpdate() throws ServerOfflineException, ItemNotFoundException,Exception {
		dummy.addProduct("101");
		dummy.addPayment(Method.CASH, 50);
		double expResult = balance + 11;
		double result = externalAccounting.getBalance();
		assertEquals(expResult, result, "external account update Faild");
	}

	@Test
	void testExternalinventoryUpdate() throws ServerOfflineException, ItemNotFoundException,Exception {
		int expResult = externalInventory.getInventoryList().get(0).getQuantity() - 1;
		dummy.addProduct("101");
		dummy.addPayment(Method.CASH, 50);
		int result = externalInventory.getInventoryList().get(0).getQuantity();
		assertEquals(expResult, result, "external inventory update Faild");
	}
}
