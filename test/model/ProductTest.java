package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ProductTest {
	Sale sale;
	Product dummy;
	Product dummy2;

	String id = "8921";
	String id2 = "9898";
	String name = "Product name";
	double price = 15;
	double vat = 10;

	@BeforeEach
	void setUp() throws Exception {
		sale = new Sale();
		dummy = new Product(id, name, price, vat, 1);
		dummy2 = new Product(id2, name, price, vat, 5);

	}

	@AfterEach
	void tearDown() throws Exception {
		sale = null;
		dummy = null;
		dummy2 = null;
	}
	
	@Test
	void testCreateProductWithQuantity() {
		int expResult = 5;
		int result = dummy2.getQuantity();
		assertEquals(expResult,result,"create an product whith several quantity Faild");
	}

	@Test
	void testGetNetPrice() {
		double expResult = price;
		double result = dummy.getNetPrice();
		assertEquals(expResult, result, "get net price for singel product input faild");
		
	}

	@Test
	void testGetName() {
		String expResult = name;
		String result = dummy.getName();
		assertEquals(expResult, result, "get name faild");
	}

	@Test
	void testGetId() {
		String expResult = id;
		String result = dummy.getId();
		assertEquals(expResult, result, "get id faild");
	}

	@Test
	void testGetVAT() {
		double expResult = vat;
		double result = dummy.getVAT();
		assertEquals(expResult, result, "get VAT faild");
	}

	@Test
	void testGetQuantity() {
		int expResult = 1;
		int result = dummy.getQuantity();
		assertEquals(expResult, result, "get quantity faild");
	}

	@Test
	void testSetQuantity() {
		dummy.setQuantity(3);
		int expResult = 3;
		int result = dummy.getQuantity();
		assertEquals(expResult, result, "set scaler quantity faild");
	}
	
	@Test
	void testSetNegativeQuantity() {
		dummy.setQuantity(-3);
		int expResult = 1;
		int result = dummy.getQuantity();
		assertEquals(expResult, result, "set negative quantity faild");
	}

	@Test
	void testAddQuantity() {
		dummy.addQuantity(1);
		int expResult = 2;
		int result = dummy.getQuantity();
		assertEquals(expResult, result, "set add quantity faild");

	}
	
	@Test
	void testAddNegativeQuantity() {
		dummy.addQuantity(-1);
		int expResult = 1;
		int result = dummy.getQuantity();
		assertEquals(expResult, result, "set add negavive quantity faild");

	}

	@Test
	void testremoveQuantity() {
		dummy.addQuantity(3);
		dummy.removeQuantity(2);
		int expResult = 2;
		int result = dummy.getQuantity();
		assertEquals(expResult, result, "remove quantity faild");
	}
	
	@Test
	void testRemoveQuantityWhithNegativeInteger() {
		dummy.addQuantity(3);
		dummy.removeQuantity(-2);
		int expResult = 2;
		int result = dummy.getQuantity();
		assertEquals(expResult, result, "remove whith negative integer faild");
	}
	@Test
	void testGrossPriceForSingelProduct() {
		double expResult = 16.5;
		double result = dummy.grossPrice();
		assertEquals(expResult, result, "gross price faild");
	}
	
	@Test
	void testGrossPriceForSeveralProduct() {
		double expResult = 82.5;
		double result = dummy2.grossPrice();
		assertEquals(expResult, result, "gross price faild");
	}
	
	@Test 
	void testEqualsTrue() {
		Product expResult = new Product(id, name, price, vat, 1);
		Product result = dummy;
		assertTrue(result.equals(expResult));
	}
	
	@Test 
	void testEqualsFalse() {
		Product expResult = new Product("123456", name, price, vat, 1);
		Product result = dummy;
		assertFalse(result.equals(expResult));
	}

}
