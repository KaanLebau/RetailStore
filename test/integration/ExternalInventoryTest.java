package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Product;

class ExternalInventoryTest {

	ExternalInventory dummy;
	// purchased products
	Product colaInv;
	Product prestOstInv;
	Product kaffeInv; 

	List<Product> purchased;

	@BeforeEach
	void setUp() throws Exception {
		dummy = new ExternalInventory();
		purchased = new ArrayList<>();
		kaffeInv = new Product("106", "Kaffe", 33);
		colaInv = new Product("101", "Cola", 12);
		prestOstInv = new Product("104", "Ost", 59);
		purchased.add(kaffeInv);
		purchased.add(colaInv);
		purchased.add(prestOstInv);
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy= null;
		purchased = null;
	}

	@Test
	void testGetItemList() {
		List<ItemDTO> testList = dummy.getItemList();
		assertEquals(dummy.getItemList(), testList,"get item list Faild");
	}

	@Test
	void testGetInventoryList() {
		List<Product> testList = dummy.getInventoryList();
		assertEquals(dummy.getInventoryList(), testList,"get inventory list Faild");
	}

	@Test
	void testSearchItem() {
		ItemDTO expResult = new ItemDTO("101", "Cola", 10, 10);
		ItemDTO result = dummy.searchItem("101");
		assertEquals(expResult, result,"search item Faild");
		
	}

	@Test
	void testSearchProduct() {
		Product expResult = new Product("101", "Cola", 12);
		Product result = dummy.searchProduct("101");
		assertEquals(expResult, result,"Search Product Faild");
		
	}

	@Test
	void testSearchItemNoItem() {
		ItemDTO result = dummy.searchItem("10");
		ItemDTO expResult = new ItemDTO();
		assertEquals(expResult, result,"Search item does not exist Faild");
	}

	@Test
	void testUpdateExternalInventory() {
		dummy.updateExternalInventory(purchased);
		Product one = dummy.searchProduct("106");
		Product zero = dummy.searchProduct("101");
		Product negativ = dummy.searchProduct("104");
		assertEquals(1, one.getQuantity());
		assertEquals(0, zero.getQuantity());
		assertEquals(-1, negativ.getQuantity()); // TODO
	}

}
