package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.Util.Category;

class SaleTest {
	Sale dummy;

	@BeforeEach
	void setUp() throws Exception {
		dummy = new Sale();
		new Product("101", "Cola", 10 , 10, dummy, 1);
		
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy = null;
		
	}

	
	@Test
	void testAddProductToSaleOneItem() {
		Product kaffe = new Product("106", "Kaffe", 34,10,dummy, 1);
		int elementNumber = dummy.getPurcheasedProducts().size();
		assertEquals(2,elementNumber);
		Product dummyItem = dummy.getPurcheasedProducts().get(1);
		assertEquals(kaffe, dummyItem);	
	}
	@Test
	void testAddProductToSaleUpdateQuantity() {
		new Product("101", "Cola", 10 , 10, dummy, 1);
		int elementNumber = dummy.getPurcheasedProducts().size();
		assertEquals(1,elementNumber);
		assertEquals(2, dummy.getPurcheasedProducts().get(0).getQuantity());	
	}
	
	@Test
	void testGetPurcheasedProducts() {
		List<Product> dummyList = dummy.getPurcheasedProducts();
		assertEquals(dummy.getPurcheasedProducts(),dummyList);
	}

	@Test
	void testGetEndSaleTotal() {
		double toPay = dummy.getEndSaleTotal();
		assertEquals (11, toPay);
		new Product("101", "Cola", 10 , 10, dummy, 1);
		toPay = dummy.getEndSaleTotal();
		assertEquals(22,toPay);

	}

	@Test
	void testUpdateSale() {

	}

	
	@Test
	void testAddItemDiscount() {
		dummy.getPurcheasedProducts().clear();
		Product prestOst = new Product("104", "Ost", 50, 10, dummy, 1);
		Discount itemDiscount = new Discount(Category.ITEM, "104",10, dummy,""); 
		int discountNumber = dummy.getRegistredDiscount().size();
		assertEquals(1, discountNumber);
		Discount testQuantity = dummy.getRegistredDiscount().get(0);
		assertEquals(itemDiscount, testQuantity);
		double cal = prestOst.grossPrice() - 10;
		assertEquals(cal, dummy.getEndSaleTotal());
	}
	
	@Test
	void testAddItemQuantityDiscount() {
		new Product("101", "Cola", 10 , 10, dummy, 1);
		new Product("101", "Cola", 10 , 10, dummy, 1);
		Discount quantityDiscount = new Discount(Category.ITEM, "101",3, 11, dummy,"");
		int discountNumber = dummy.getRegistredDiscount().size();
		assertEquals(1, discountNumber);
		Discount testQuantity = dummy.getRegistredDiscount().get(0);
		assertEquals(quantityDiscount, testQuantity);
		assertEquals(22,dummy.getEndSaleTotal());
	}
	
	@Test
	void testAddCustomerDiscount() {
		dummy.getPurcheasedProducts().clear();
		new Product("104", "Ost", 58, 10, dummy, 1);
		Discount customerDiscount = new Discount(Category.CUSTOMER, "104",10, dummy,""); 
		int discountNumber = dummy.getRegistredDiscount().size();
		assertEquals(1, discountNumber);
		Discount testQuantity = dummy.getRegistredDiscount().get(0);
		assertEquals(customerDiscount, testQuantity);
		assertEquals(57.42,dummy.getEndSaleTotal());
	}
	
	@Test
	void testSeveralItemDiscount() {
		
		new Product("101", "Cola", 10 , 10, dummy, 1);
		new Product("101", "Cola", 10 , 10, dummy, 1);
		new Product("104", "Ost", 50, 10, dummy, 1);
		new Discount(Category.QUANTITY, "101",3, 11, dummy,"");
		new Discount(Category.ITEM, "104",10, dummy,""); 
		int discountNumber = dummy.getRegistredDiscount().size();
		assertEquals(2, discountNumber);
		assertEquals(67,dummy.getEndSaleTotal());
	}
	
	@Test
	void testSeveralQuantityDiscount() {
		new Product("101", "Cola", 10 , 10, dummy, 1);
		new Product("101", "Cola", 10 , 10, dummy, 1);
		new Product("101", "Cola", 10 , 10, dummy, 1);
		new Product("101", "Cola", 10 , 10, dummy, 1);
		new Product("101", "Cola", 10 , 10, dummy, 1);
		new Discount(Category.QUANTITY, "101",3, 11, dummy,"");
		int discountNumber = dummy.getRegistredDiscount().size();
		assertEquals(2, discountNumber,"number of discount in several qantity discount faild");
		assertEquals(44,dummy.getEndSaleTotal(),"several quantity discount test faild");
	}
	
	@Test
	void testSeveralManuelInputQuantityDiscount() {
		new Product("101", "Cola", 10 , 10, dummy, 5);
		new Discount(Category.QUANTITY, "101",3, 11, dummy,"take 3 pay 2");
		int discountNumber = dummy.getRegistredDiscount().size();
		assertEquals(2, discountNumber,"number of discount in several qantity discount faild");
		assertEquals(44,dummy.getEndSaleTotal(),"several quantity discount test faild");
	}

	
	@Test
	void testAllDiscountTyps() {
		
		new Product("101", "Cola", 10 , 10, dummy, 1);
		new Product("101", "Cola", 10 , 10, dummy, 1);
		new Product("104", "Ost", 50, 10, dummy, 1);
		new Discount(Category.QUANTITY, "101",3, 11, dummy,"");
		new Discount(Category.ITEM, "104",10, dummy,""); 
		new Discount(Category.CUSTOMER, "989898", 50, dummy,"");
		int expResultSize = 3;
		int resultSize = dummy.getRegistredDiscount().size();
		assertEquals(expResultSize, resultSize,"number of discounts in all three discount Faild");
		double expResult = 33.5;
		double result =dummy.getEndSaleTotal();
		assertEquals(expResult, result,"all three discount typs applyd sale faild");
	}
	

}
