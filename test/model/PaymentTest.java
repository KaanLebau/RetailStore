package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import integration.Address;
import integration.Printer;
import util.Util.Category;
import util.Util.Method;

class PaymentTest {
	Sale sale;
	Address storeAddress;
	Printer printer;
	CashRegister cashRegister;
	Payment dummy;
	double amountpaid = 100;
	
	@BeforeEach
	void setUp() throws Exception {
		sale = new Sale();
		printer = new Printer();
		storeAddress = new Address("G�teborg", "andra l�ng",12, 42427);
		cashRegister = new CashRegister(storeAddress, printer);
		dummy = new Payment(Method.CASH, amountpaid, sale,cashRegister);
		
		new Product("101", "Cola", 10 , 10, sale, 1);
		new Product("101", "Cola", 10 , 10, sale, 1);
		new Product("101", "Cola", 10 , 10, sale, 1);
		new Product("104", "Ost", 50, 10, sale, 1);
		new Discount(Category.QUANTITY, "101",3, 11, sale,"");
		new Discount(Category.ITEM, "104",10, sale,"");
		new Discount(Category.CUSTOMER,"232323", 5, sale,"");
	}

	@AfterEach
	void tearDown() throws Exception {
		//sale = null;
		printer = null;
		storeAddress = null;
		cashRegister  = null;
		dummy = null;
		sale.getPurcheasedProducts().clear();
		sale.getRegistredDiscount().clear();
	}

	@Test
	void testGetMethodForCash() {
		Method expResult = Method.CASH;
		Method result =  dummy.getMethod();
		assertEquals(expResult,result , "get method for cash Faild");
	}
	@Test
	void testGetMethodForCardterminal() {
		Payment dummy2 = new Payment(Method.CARDTERMINAL, sale,cashRegister);
		Method expResult = Method.CARDTERMINAL;
		Method result = dummy2.getMethod();
		assertEquals(expResult, result,"get method for cardterminal Faild");
	}

	@Test
	void testGetPaymentDoneTrue() {
		boolean expResult = true;
		boolean result =dummy.getPaymentDone();
		assertEquals(expResult ,result, "get payment done Faild" );
	}
	@Test
	void testGetPaymentDoneFlase() {
		Payment dummy2 = new Payment();
		boolean expResult = false;
		boolean result =dummy2.getPaymentDone();
		assertEquals(expResult ,result, "get payment done Faild" );
	}
	
	@Test
	void testGetAmount() {
		double expResult = amountpaid;
		double result =dummy.getAmount();
		assertEquals(expResult ,result, "get amount paid Faild" );
	}

	@Test
	void testGetPurchasedProductList() {
		List<Product> expResult = sale.getPurcheasedProducts();
		List<Product> result =  dummy.getPurchasedProductList();
		assertEquals(expResult,result, "get purchased product list Faild" );
	}

	@Test
	void testGetDiscpountList() {
		List<Discount> expResult = sale.getRegistredDiscount();
		List<Discount> result =  dummy.getDiscountList();
		assertEquals(expResult, result,"get discount list Faild");
	}

	@Test
	void testGetTotalCost() {
		double expResult =sale.getEndSaleTotal();
		double result = dummy.getTotalCost(); 
		assertEquals(expResult , result, "get final total price Faild");
	}

	@Test
	void testAmountChange() {
		double expResult= 36.35;
		double result = dummy.getAmountChange();
		assertEquals(expResult, result, "amount change Faild");
	}

	@Test
	void testGetCashRegister() {
		CashRegister expResult = cashRegister;
		CashRegister result = dummy.getCashRegister();
		assertEquals(expResult, result,"get cash register Faild");
	}

	@Test
	void testGetDate() {
		Date expResult = sale.getDate();
		Date result = dummy.getDate();
		assertEquals(expResult, result, "get date Faild");
	}

}
