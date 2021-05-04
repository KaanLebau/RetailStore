package integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.Util.Category;

class DiscountTypDTOTest {
	DiscountTypDTO itemDiscount;
	DiscountTypDTO quantityDiscount;
	DiscountTypDTO customerDiscount;
	@BeforeEach
	void setUp() throws Exception {
		itemDiscount = new DiscountTypDTO(Category.ITEM, "100", 10, "Campaing product");
		quantityDiscount = new DiscountTypDTO (Category.QUANTITY,"100",3, 1,"Buy 3 pay for 2");
		customerDiscount = new DiscountTypDTO(Category.CUSTOMER, "232323", 5,"5% loyality discount");
	}

	@AfterEach
	void tearDown() throws Exception {
		itemDiscount = null;
		quantityDiscount = null;
		customerDiscount = null;
	}

	@Test
	void testGetDiscountId() {
		assertEquals("0",itemDiscount.getDiscountId(),"get discount id fails in item");
		assertEquals("0",quantityDiscount.getDiscountId(),"get discount id fails in quantity");
		assertEquals("232323",customerDiscount.getDiscountId(),"get discount id fails in customer");
		
	}

	@Test
	void testGetCategory() {
		assertEquals(Category.ITEM,itemDiscount.getCategory(),"get discount Category fails in item");
		assertEquals(Category.QUANTITY,quantityDiscount.getCategory(),"get Category id fails in quantity");
		assertEquals(Category.CUSTOMER,customerDiscount.getCategory(),"get Category id fails in customer");
		
		
	}

	@Test
	void testGetDiscountPercent() {
		assertEquals(1,itemDiscount.getDiscountPercent(),"get discount percent id fails in item");
		assertEquals(1,quantityDiscount.getDiscountPercent(),"get discount percent id fails in quantity");
		assertEquals(0.95,customerDiscount.getDiscountPercent(),"get discount percent id fails in customer");
		
	}

	@Test
	void testGetDiscountAmount() {
		assertEquals(10,itemDiscount.getDiscountAmount(),"get Category id fails in item");
		assertEquals(1,quantityDiscount.getDiscountAmount(),"get Category id fails in quantity");
		assertEquals(0,customerDiscount.getDiscountAmount(),"get Category id fails in customer");
	}

	@Test
	void testGetItemId() {
		assertEquals("100",itemDiscount.getItemId(),"get item id fails in item");
		assertEquals("100",quantityDiscount.getItemId(),"get item id fails in quantity");
		assertEquals("0",customerDiscount.getItemId(),"get item id fails in customer");
	}

	@Test
	void testGetItemQuantity() {
		assertEquals(1,itemDiscount.getItemQuantity(),"get item quantity fails in item");
		assertEquals(3,quantityDiscount.getItemQuantity(),"get item quantity fails in quantity");
		assertEquals(1,customerDiscount.getItemQuantity(),"get item quantity fails in customer");
	}
	
	@Test
	void testGetdescription() {
		assertEquals("Campaing product",itemDiscount.getDescription(),"get item description fails in item");
		assertEquals("Buy 3 pay for 2",quantityDiscount.getDescription(),"get item description fails in quantity");
		assertEquals("5% loyality discount",customerDiscount.getDescription(),"get item description fails in customer");
	}
	
	@Test
	void testEquals() {
		DiscountTypDTO test = new DiscountTypDTO(Category.ITEM, "100", 10, "Campaing product");
		assertTrue(test.equals(itemDiscount),"discount compare fails");
	}
	

}
