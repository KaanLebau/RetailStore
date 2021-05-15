package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integration.Server.Connection;
import model.Product;
import util.exceptions.ItemNotFoundException;
import util.exceptions.ServerOfflineException;

class ExternalInventoryTest {

	ExternalInventory dummy;
	// purchased products
	Product colaInv;
	Product prestOstInv;
	Product kaffeInv; 
	List<Product> purchased;
	
	private ByteArrayOutputStream printoutBuffer;
	private PrintStream originalSysOut;

	@BeforeEach
	void setUp() throws Exception {
		dummy = new ExternalInventory();
		purchased = new ArrayList<>();
		kaffeInv = new Product("106", "Kaffe", 33);
		colaInv = new Product("101", "Cola", 10);
		prestOstInv = new Product("104", "Ost", 59);
		purchased.add(kaffeInv);
		purchased.add(colaInv);
		purchased.add(prestOstInv);
		
		printoutBuffer = new ByteArrayOutputStream();
		PrintStream inMemSysOut = new PrintStream(printoutBuffer);
		originalSysOut = System.out;
		System.setOut(inMemSysOut);
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy= null;
		purchased = null;
		
		printoutBuffer = null;
		System.setOut(originalSysOut);
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
	void testSearchItem() throws ItemNotFoundException {
		ItemDTO expResult = new ItemDTO("101", "Cola", 10, 10);
		ItemDTO result = dummy.searchItem("101", 1);
		assertEquals(expResult, result,"search item Faild");
		
	}

	@Test
	void testSearchItemNoItem() throws ItemNotFoundException {
		try {
			dummy.searchItem("10",1);
			fail("Item not found exception faild");
		} catch (ItemNotFoundException e) {
		String expResult = "searchItem";
		assertTrue(e.getMessage().contains(expResult),"Item not found exception wrong msg");
		}		
	}

	@Test
	void testUpdateExternalInventory() {
		dummy.updateExternalInventory(purchased);
		Product one = searchProduct("106");
		Product zero = searchProduct("101");
		Product negativ = searchProduct("104");
		assertEquals(1, one.getQuantity());
		assertEquals(0, zero.getQuantity());
		assertEquals(-1, negativ.getQuantity());
	}
	private Product searchProduct(String itemId) {
		Product foundProduct = new Product();
		for(Product search : dummy.getInventoryList())
			if(search.getId().equalsIgnoreCase(itemId)) {
				foundProduct = search;
				break;
			}
		return foundProduct;
	}

	@Test
	void testConnectionEstablished() throws ServerOfflineException {
		
		try {
			dummy.connectionControl();			
		}catch(ServerOfflineException e) {
			fail("Server online faild");
		}
	}

	@Test
	void testConnectionNotEstablished() throws ServerOfflineException {
		dummy.setConnectionStatus(Connection.OFFLINE);
		try {
			dummy.connectionControl();
			fail("connection exception faild");
		} catch(ServerOfflineException e){
        String expectedOutput = "faild";
        assertTrue(e.getMessage().contains(expectedOutput), "connecting ofline server exception faild");
		}
	}
	
	@Test
	void testServerNameInException() throws ServerOfflineException {
		dummy.setConnectionStatus(Connection.OFFLINE);
		try {
			dummy.connectionControl();
			fail("connection exception faild");
		} catch(ServerOfflineException e){
        String expectedOutput = "INVENTORY";
        assertTrue(e.getMessage().contains(expectedOutput), "server specifier faild");
		}
	}
	
}
