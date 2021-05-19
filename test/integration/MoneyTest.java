package integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.enums.Type;

class MoneyTest {
	Money dummy;
	int result;
	int expResult;

	@BeforeEach
	void setUp() throws Exception {
		dummy = new Money(500, 1, Type.FIVEHUNDRED);
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy = null;
	}

	@Test
	void testGetValue() {
		result = dummy.getValue();
		expResult = 500;
		assertEquals(expResult, result, "test get value Faild");
	}

	@Test
	void testSetValue() {
		dummy.setValue(100);
		result = dummy.getValue();
		expResult = 100;
		assertEquals(expResult, result, "test set value Faild");
	}

	@Test
	void testGetQuantity() {
		result = dummy.getQuantity();
		expResult = 1;
		assertEquals(expResult, result, "test get quantity Faild");
	}

	@Test
	void testSetQuantity() {
		dummy.setQuantity(100);
		result = dummy.getQuantity();
		expResult = 100;
		assertEquals(expResult, result, "test set quantity Faild");
	}

	@Test
	void testAddQuantity() {
		dummy.addQuantity(1);
		result = dummy.getQuantity();
		expResult = 2;
		assertEquals(expResult, result, "test add quantity Faild");
	}

	@Test
	void testRemoveFromQuantity() {
		dummy.removeFromQuantity(1);
		result = dummy.getQuantity();
		expResult = 0;
		assertEquals(expResult, result, "test remove from quantity Faild");
	}

	@Test
	void testGetQuantityInChange() {
		result = dummy.getQuantityInChange();
		expResult = 0;
		assertEquals(expResult, result, "test get quantity in change Faild");
	}

	@Test
	void testSetQuantityInChange() {
		dummy.setQuantityInChange(1);
		result = dummy.getQuantityInChange();
		expResult = 1;
		assertEquals(expResult, result, "test set quantity  in change Faild");
	}

	@Test
	void testGetType() {
		assertEquals(Type.FIVEHUNDRED, dummy.getType(), "get typ faild");
	}

	@Test
	void testSetType() {
		dummy.setType(Type.FIFTY);
		assertEquals(Type.FIFTY,dummy.getType(),"set type faild");

	}

	

	@Test
	void testGetCurrency() {
		assertEquals("USD",dummy.getCurrency(),"get currency faild");

	}

	@Test
	void testToString() {

	}

	@Test
	void testEqualsObject() {
		Money money = new Money(500, 1, Type.FIVEHUNDRED);
		assertTrue(dummy.equals(money),"equal metod faild");
	}

}
