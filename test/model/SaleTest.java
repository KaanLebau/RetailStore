package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integration.DiscountDTO;
import util.Util.Category;

class SaleTest {
	Sale dummy;

	@BeforeEach
	void setUp() throws Exception {
		dummy = new Sale();
		Product item1 = new Product("101", "Cola", 10 , 10, 1);
		dummy.addProductToSale(item1);
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy = null;
		
	}

	
	@Test
	void testAddProductToSaleOneItem() {
		Product kaffe = new Product("106", "Kaffe", 34,10, 1);
		dummy.addProductToSale(kaffe);
		int elementNumber = dummy.getPurcheasedProducts().size();
		assertEquals(2,elementNumber);
		Product dummyItem = dummy.getPurcheasedProducts().get(1);
		assertEquals(kaffe, dummyItem);	
	}
	@Test
	void testAddProductToSaleUpdateQuantity() {
		Product item1 = new Product("101", "Cola", 10 , 10, 1);
		dummy.addProductToSale(item1);
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
		Product item1 = new Product("101", "Cola", 10 , 10, 1);
		dummy.addProductToSale(item1);
		toPay = dummy.getEndSaleTotal();
		assertEquals(22,toPay);

	}

	@Test
	void testUpdateSale() {

	}

	
	@Test
	void testAddItemDiscount() {
		dummy.getPurcheasedProducts().clear();
		Product prestOst = new Product("104", "Ost", 50, 10, 1);
		dummy.addProductToSale(prestOst);
		DiscountDTO itemDiscount = new DiscountDTO(Category.ITEM, "104",10,""); 
		dummy.addItemDiscount(itemDiscount);
		int discountNumber = dummy.getRegistredDiscount().size();
		assertEquals(1, discountNumber);
		DiscountDTO testQuantity = dummy.getRegistredDiscount().get(0);
		assertEquals(itemDiscount, testQuantity);
		double cal = prestOst.grossPrice() - 10;
		assertEquals(cal, dummy.getEndSaleTotal());
	}
	
	@Test
	void testAddItemQuantityDiscount() {
		Product item1 = new Product("101", "Cola", 10 , 10, 1);
		Product item2 = new Product("101", "Cola", 10 , 10, 1);
		dummy.addProductToSale(item2);
		dummy.addProductToSale(item1);
		DiscountDTO quantityDiscount = new DiscountDTO(Category.ITEM, "101",3, 11,"");
		dummy.addItemDiscount(quantityDiscount);
		int discountNumber = dummy.getRegistredDiscount().size();
		assertEquals(1, discountNumber);
		DiscountDTO testQuantity = dummy.getRegistredDiscount().get(0);
		assertEquals(quantityDiscount, testQuantity);
		assertEquals(22,dummy.getEndSaleTotal());
	}
	
	@Test
	void testAddCustomerDiscount() {
		dummy.getPurcheasedProducts().clear();
		Product item1 = new Product("104", "Ost", 58, 10, 1);
		dummy.addProductToSale(item1);
		DiscountDTO customerDiscount = new DiscountDTO(Category.CUSTOMER, "104",10, ""); 
		dummy.addCustomerDiscount(customerDiscount);
		int discountNumber = dummy.getRegistredDiscount().size();
		assertEquals(1, discountNumber);
		DiscountDTO testQuantity = dummy.getRegistredDiscount().get(0);
		assertEquals(customerDiscount, testQuantity);
		assertEquals(57.42,dummy.getEndSaleTotal());
	}
	
	@Test
	void testSeveralItemDiscount() {
		
		Product item1 = new Product("101", "Cola", 10 , 10, 1);
		Product item2 = new Product("101", "Cola", 10 , 10, 1);
		Product item3 = new Product("104", "Ost", 50, 10, 1);
		dummy.addProductToSale(item3);
		dummy.addProductToSale(item2);
		dummy.addProductToSale(item1);
		DiscountDTO quantityDiscount = new DiscountDTO(Category.QUANTITY, "101",3, 11,"");
		DiscountDTO itemDiscount = new DiscountDTO(Category.ITEM, "104",10, ""); 
		dummy.addItemDiscount(itemDiscount);
		dummy.addItemDiscount(quantityDiscount);
		int discountNumber = dummy.getRegistredDiscount().size();
		assertEquals(2, discountNumber);
		assertEquals(67,dummy.getEndSaleTotal());
	}
	
	@Test
	void testSeveralQuantityDiscount() {
		Product item1 = new Product("101", "Cola", 10 , 10, 1);
		Product item2 = new Product("101", "Cola", 10 , 10, 1);
		Product item3 = new Product("101", "Cola", 10, 10, 1);
		Product item4 = new Product("101", "Cola", 10 , 10, 1);
		Product item5 = new Product("101", "Cola", 10, 10, 1);
		dummy.addProductToSale(item5);
		dummy.addProductToSale(item4);
		dummy.addProductToSale(item3);
		dummy.addProductToSale(item2);
		dummy.addProductToSale(item1);
		DiscountDTO quantityDiscount = new DiscountDTO(Category.QUANTITY, "101",3, 11, "");
		dummy.addItemDiscount(quantityDiscount);
		int discountNumber = dummy.getRegistredDiscount().size();
		assertEquals(2, discountNumber,"number of discount in several qantity discount faild");
		assertEquals(44,dummy.getEndSaleTotal(),"several quantity discount test faild");
	}
	
	@Test
	void testSeveralManuelInputQuantityDiscount() {
		Product item1 = new Product("101", "Cola", 10 , 10, 5);
		dummy.addProductToSale(item1);
		DiscountDTO quantityDiscount = new DiscountDTO(Category.QUANTITY, "101",3, 11,"take 3 pay 2");
		dummy.addItemDiscount(quantityDiscount);
		int discountNumber = dummy.getRegistredDiscount().size();
		assertEquals(2, discountNumber,"number of discount in several qantity discount faild");
		assertEquals(44,dummy.getEndSaleTotal(),"several quantity discount test faild");
	}

	
	@Test
	void testAllDiscountTyps() {
		
		Product item1 = new Product("101", "Cola", 10 , 10, 1);
		Product item2 = new Product("101", "Cola", 10 , 10, 1);
		Product item3 = new Product("104", "Ost", 50, 10, 1);
		dummy.addProductToSale(item3);
		dummy.addProductToSale(item2);
		dummy.addProductToSale(item1);
		DiscountDTO quantityDiscount = new DiscountDTO(Category.QUANTITY, "101",3, 11, "");
		DiscountDTO itemDiscount = new DiscountDTO(Category.ITEM, "104",10, ""); 
		DiscountDTO customerDiscount = new DiscountDTO(Category.CUSTOMER, "989898", 50, "");
		dummy.addCustomerDiscount(customerDiscount);
		dummy.addItemDiscount(itemDiscount);
		dummy.addItemDiscount(quantityDiscount);
		int expResultSize = 3;
		int resultSize = dummy.getRegistredDiscount().size();
		assertEquals(expResultSize, resultSize,"number of discounts in all three discount Faild");
		double expResult = 33.5;
		double result =dummy.getEndSaleTotal();
		assertEquals(expResult, result,"all three discount typs applyd sale faild");
	}
	

}
