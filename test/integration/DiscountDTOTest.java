package integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integration.DiscountDTO;
import model.Sale;
import util.Util.Category;

class DiscountDTOTest {
	Sale sale;
	DiscountDTO dummyItem;
	DiscountDTO dummyQuantity;
	DiscountDTO dummyCustomer;

	String defaultdiscountId = "0";
	String defaultItemId = "0";
	double defaultDiscountPercent = 1;
	double defaultDiscountAmount = 0;
	int defaultItemQuantity = 1;

	@BeforeEach
	void setUp() throws Exception {
		sale = new Sale();
		dummyItem = new DiscountDTO(Category.ITEM, "232323", 15, "Item discount");
		dummyQuantity = new DiscountDTO(Category.QUANTITY, "242424", 3, 10, "Quantity discount");
		dummyCustomer = new DiscountDTO(Category.CUSTOMER, "121212", 10, "Customer discount");
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
		boolean result = (dummyItem instanceof DiscountDTO);
		assertTrue(result, "dummyItem is instance of Discount Faild");
		result = (dummyItem instanceof DiscountDTO);
		assertTrue(result, "dummyItem is instance of Discount Faild");
		result = (dummyItem instanceof DiscountDTO);
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
		DiscountDTO result = dummyItem;
		assertTrue(dummyItem.equals(result),"discount equal Faild");
	}

	@Test
	void testEqualFlaseWrongItemId() {
		DiscountDTO result = new DiscountDTO(Category.ITEM, "212121", 15, "Item discount");
		DiscountDTO expResult = dummyItem;
		assertFalse(expResult.equals(result),"discount equal Faild");
	}
	
	@Test
	void testEqualFlaseWrongCategory() {
		DiscountDTO result = new DiscountDTO(Category.QUANTITY, "232323", 3, 15,"Item discount");
		DiscountDTO expResult = dummyItem;
		assertFalse(expResult.equals(result),"discount equal Faild");
	}
	
	@Test
	void testEqualFlaseWrongDiscountId() {
		DiscountDTO result = new DiscountDTO(Category.CUSTOMER, "131313", 10, "Customer discount");
		DiscountDTO expResult = dummyCustomer;
		assertFalse(expResult.equals(result),"discount equal Faild");
	}
	
}
