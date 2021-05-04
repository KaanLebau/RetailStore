package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.Util.Category;

class DiscountTest {
	Sale sale;
	Discount dummyItem;
	Discount dummyQuantity;
	Discount dummyCustomer;

	String defaultdiscountId = "0";
	String defaultItemId = "0";
	double defaultDiscountPercent = 1;
	double defaultDiscountAmount = 0;
	int defaultItemQuantity = 1;

	@BeforeEach
	void setUp() throws Exception {
		sale = new Sale();
		dummyItem = new Discount(Category.ITEM, "232323", 15, sale, "Item discount");
		dummyQuantity = new Discount(Category.QUANTITY, "242424", 3, 10, sale, "Quantity discount");
		dummyCustomer = new Discount(Category.CUSTOMER, "121212", 10, sale, "Customer discount");
	}

	@AfterEach
	void tearDown() throws Exception {
		sale = null;
		dummyItem = null;
		dummyQuantity = null;
		dummyCustomer = null;
	}

	@Test
	void testDiscount() {
		boolean result = (dummyItem instanceof Discount);
		assertTrue(result, "dummyItem is instance of Discount Faild");
		result = (dummyItem instanceof Discount);
		assertTrue(result, "dummyItem is instance of Discount Faild");
		result = (dummyItem instanceof Discount);
		assertTrue(result, "dummyItem is instance of Discount Faild");

	}

	@Test
	void testGetDiscountIdForItemDiscounts() {
		String expResult = defaultdiscountId;
		String result = dummyItem.getDiscountId();
		assertEquals(expResult, result, "get discount id for ITEM discount Faild");
		result = dummyQuantity.getDiscountId();
		assertEquals(expResult, result, "get discount id for QUANTITY discount Faild");

	}
	@Test
	void testGetDiscountIdForCustomerDiscounts() {
		String expResult = "121212";
		String result = dummyCustomer.getDiscountId();
		assertEquals(expResult, result, "get discount id for CUSTOMER Faild");

	}

	@Test
	void testGetCategori() {
		Category expResult = Category.ITEM;
		Category result = dummyItem.getCategory();
		assertEquals(expResult, result, "get discount Category for ITEM discount Faild");
		expResult = Category.QUANTITY;
		result = dummyQuantity.getCategory();
		assertEquals(expResult, result, "get discount Category for QUANTITY discount Faild");
		expResult = Category.CUSTOMER;
		result = dummyCustomer.getCategory();
		assertEquals(expResult, result, "get discount Category for CUSTOMER discount Faild");
	}
	@Test
	void testGetDiscountPercentForItem() {
		double expResult = defaultDiscountPercent;
		double result = dummyItem.getDiscountPercent();
		assertEquals(expResult, result, "get discount percent for ITEM Faild");
		result = dummyQuantity.getDiscountPercent();
		assertEquals(expResult, result, "get discount percent for QUANTITY Faild");
	}
	@Test
	void testGetDiscountPercentForCustomer() {
		double expResult = 0.9;
		double result = dummyCustomer.getDiscountPercent();
		assertEquals(expResult, result, "get discount percent for CUSTOMER Faild");
	}
	@Test
	void testGetDiscountAmountForItem() {
		double expResult = 15;
		double result = dummyItem.getDiscountAmount();
		assertEquals(expResult, result, "get discount amount for ITEM Faild");
		expResult = 10;
		result = dummyQuantity.getDiscountAmount();
		assertEquals(expResult, result, "get discount amount for QUANTITY Faild");
	}

	@Test
	void testGetDiscountAmountForCustomer() {
		double expResult = defaultDiscountAmount;
		double result = dummyCustomer.getDiscountAmount();
		assertEquals(expResult, result, "get discount amount for CUSTOMER Faild");
	}

	@Test
	void testGetItemIdForItem() {
		String expResult = "232323";
		String result = dummyItem.getItemId();
		assertEquals(expResult, result, "get item id for ITEM Faild");
		expResult = "242424";
		result = dummyQuantity.getItemId();
		assertEquals(expResult, result, "get item id for QUANTITY Faild");

	}

	@Test
	void testGetItemIdForCustomer() {
		String expResult = defaultItemId;
		String result = dummyCustomer.getItemId();
		assertEquals(expResult, result, "get item id for CUSTOMER Faild");
	}

	@Test
	void testGetItemQuantity() {
		int expResult = 1;
		int result = dummyItem.getItemQuantity();
		assertEquals(expResult, result, "get intem quantity for ITEM Faild");
		expResult = 3;
		result = dummyQuantity.getItemQuantity();
		assertEquals(expResult, result, "get intem quantity for QUANTITY Faild");
		expResult = 1;
		result = dummyCustomer.getItemQuantity();
		assertEquals(expResult, result, "get intem quantity for CUSTOMER Faild");
	}

	@Test
	void testSetDiscountId() {
		dummyCustomer.setDiscountId("989898");
		String expResult = "989898";
		String result = dummyCustomer.getDiscountId();
		assertEquals(expResult, result, "set discount id for CUSTOMER Faild");
	}
	@Test
	void testsetDiscountIdWrongCategory() {
		dummyItem.setDiscountId("313131");
		String expResult= defaultdiscountId;
		String result = dummyItem.getDiscountId();
		assertEquals(expResult,result,"set discout id for ITEM Faild");
		dummyQuantity.setDiscountId("2");
		result = dummyQuantity.getDiscountId();
		assertEquals(expResult,result,"set discout id  for QUANTITY Faild");

	}
	@Test
	void testsetDiscountPercentWrongCategory() {
		dummyItem.setDiscountPercent(12);
		double expResult= defaultDiscountPercent;
		double result = dummyItem.getDiscountPercent();
		assertEquals(expResult,result,"set discout percent  for ITEM Faild");
		dummyQuantity.setDiscountPercent(30);
		result = dummyQuantity.getDiscountPercent();
		assertEquals(expResult,result,"set discout percent  for QUANTITY Faild");

	}

	@Test
	void testSetDiscountPercentInputGreaterThenOne() {
		dummyCustomer.setDiscountPercent(10);
		double expResult = 0.9;
		double result =  dummyCustomer.getDiscountPercent();
		assertEquals(expResult,result,"set discout percent for x > 1 Faild");
	}

	@Test
	void testSetDiscountPercentInputBetweenZeroAndOne() {
		dummyCustomer.setDiscountPercent(0.8);
		double expResult = 0.8;
		double result =  dummyCustomer.getDiscountPercent();
		assertEquals(expResult,result,"set discout percent for 0 < x < 1 Faild");
	}

	@Test
	void testSetDiscountPercentInputEqualOne() {
		dummyCustomer.setDiscountPercent(1);
		double expResult = 0.99;
		double result =  dummyCustomer.getDiscountPercent();
		assertEquals(expResult,result,"set discout for percent x == 1 Faild");
	}

	@Test
	void testSetDiscountPercentInputEqualZero() {
		dummyCustomer.setDiscountPercent(0);
		double expResult = 1;
		double result =  dummyCustomer.getDiscountPercent();
		assertEquals(expResult,result,"set discout for percent x == 0 Faild");
	}

	@Test
	void testSetDiscountAmount() {
		dummyItem.setDiscountAmount(10);
		double expResult = 10;
		double result = dummyItem.getDiscountAmount();
		assertEquals(expResult, result,"set discount amount for ITEM Faild");
		dummyQuantity.setDiscountAmount(50);
		expResult = 50;
		result =  dummyQuantity.getDiscountAmount();
		assertEquals(expResult, result,"set discount amount for QUANTITY Faild");
	}
	
	@Test
	void testSetDiscountAmountWrongCategory() {
		dummyCustomer.setDiscountAmount(10);
		double expResult = 0;
		double result = dummyCustomer.getDiscountAmount();
		assertEquals(expResult, result, "set discount amount wrong category for CUSTOMER Faild");

	}

	@Test
	void testSetItemId() {
		dummyItem.setItemId("33333");
		String expResult = "33333";
		String result = dummyItem.getItemId();
		assertEquals(expResult, result, "set item id for ITEM Faild");
		dummyQuantity.setItemId("565656");
		expResult = "565656";
		result = dummyQuantity.getItemId();
		assertEquals(expResult, result, "get item id for QUANTITY Faild");
	}
	
	@Test
	void testSetItemIdWrongCategory() {
		dummyCustomer.setItemId("33333");
		String expResult = "0";
		String result = dummyCustomer.getItemId();
		assertEquals(expResult, result, "set item id wrong category for CUSTOMER Faild");

	}

	@Test
	void testSetItemQuantity() {
		dummyQuantity.setItemQuantity(5);
		int expResult = 5;
		int result =  dummyQuantity.getItemQuantity();
		assertEquals(expResult, result, "set item quantity Faild");
	}

	@Test
	void testSetItemQuantityWrongCategoryItem() {
		dummyItem.setItemQuantity(5);
		int expResult = 1;
		int result =  dummyItem.getItemQuantity();
		assertEquals(expResult, result, "set item quantity wrong category ITEM Faild");
	}
	
	@Test
	void testSetItemQuantityWrongCategoryCustomer() {
		dummyCustomer.setItemQuantity(5);
		int expResult = 1;
		int result =  dummyCustomer.getItemQuantity();
		assertEquals(expResult, result, "set item quantity wrong category CUSTOMER Faild");
	}
	
	@Test
	void testGetDescription() {
		String expResult = "Item discount";
		String result = dummyItem.getDescription();
		assertEquals(expResult, result, "get description for item Faild");
		expResult = "Quantity discount";
		result = dummyQuantity.getDescription();
		assertEquals(expResult, result, "get description for quantity Faild");
		expResult = "Customer discount";
		result = dummyCustomer.getDescription();
		assertEquals(expResult, result, "get description for customer Faild");
	}
	
	@Test
	void testEqualTrue() {
		Discount result = dummyItem;
		assertTrue(dummyItem.equals(result),"discount equal Faild");
	}

	@Test
	void testEqualFlaseWrongItemId() {
		Discount result = new Discount(Category.ITEM, "212121", 15, sale, "Item discount");
		Discount expResult = dummyItem;
		assertFalse(expResult.equals(result),"discount equal Faild");
	}
	
	@Test
	void testEqualFlaseWrongCategory() {
		Discount result = new Discount(Category.QUANTITY, "232323", 3, 15, sale, "Item discount");
		Discount expResult = dummyItem;
		assertFalse(expResult.equals(result),"discount equal Faild");
	}
	
	@Test
	void testEqualFlaseWrongDiscountId() {
		Discount result = new Discount(Category.CUSTOMER, "131313", 10, sale, "Customer discount");
		Discount expResult = dummyCustomer;
		assertFalse(expResult.equals(result),"discount equal Faild");
	}
	
}
