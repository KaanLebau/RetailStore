package integration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.Util.Category;

class DiscountRegisterTest {
	Date toDay = new Date();
	DiscountRegister dummy;
	List<DiscountDTO> dummyList;
	DiscountDTO singelItemDiscount;
	DiscountDTO quantityDiscount;
	DiscountDTO customerDiscount;
	boolean customer = true;

	@BeforeEach
	void setUp() throws Exception {
		toDay = new Date();
		dummy = new DiscountRegister();
		dummyList = new ArrayList<>();
		singelItemDiscount = new DiscountDTO(Category.ITEM, "104", 10, "Campaing product");
		quantityDiscount = new DiscountDTO(Category.QUANTITY, "101", 3, 11, "Buy 3 pay for 2");
		customerDiscount = new DiscountDTO(Category.CUSTOMER, "456", 12, "12% discount ");
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy = null;
		dummyList = null;
		singelItemDiscount = null;
		quantityDiscount = null;
		customerDiscount = null;
		
	}

	
	
	@Test
	void testUpdateList() {
		dummy.updateList(dummyList);
		assertEquals(dummyList, dummy.getList(),"update discount resgister faild");
		
	}
	
	@Test
	void testSearchCustomerDiscount() {
		DiscountDTO expResult =customerDiscount;
		DiscountDTO resultat = dummy.searchCustomerDiscount("456", customer); 
		assertEquals(expResult,resultat,"Search customer discount fails");
		
	}
	@Test
	void testSearchCustomerDiscountNoCustom() {
		customer = false;
		DiscountDTO expResult = new DiscountDTO();
		DiscountDTO result = dummy.searchCustomerDiscount("123", customer);
		assertTrue(result.equals(expResult),"discount request with invalid customer id Faild");
	}
	
	@Test
	void testSearchCustomerDiscountNotExist() {
		DiscountDTO expResult = new DiscountDTO();
		DiscountDTO result = dummy.searchCustomerDiscount("487", customer);
		assertEquals(expResult.getCategory(),result.getCategory(),
				"search customer discount invalid Discount default category Faild");
		assertEquals(expResult.getDiscountAmount(),result.getDiscountAmount(),
				"search customer discount invalid Discount default discount amount Faild");
		assertEquals(expResult.getDiscountId(),result.getDiscountId(),
				"search customer discount invalid Discount default discount id Faild");
		assertEquals(expResult.getDiscountPercent(),result.getDiscountPercent(),
				"search customer discount invalid Discount default discount percent Faild");
		assertEquals(expResult.getItemId(),result.getItemId(),
				"search customer discount invalid Discount default item id Faild");
		assertEquals(expResult.getItemQuantity(),result.getItemQuantity(),
				"search customer discount invalid Discount default quantity Faild");
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
	
}
