package integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemDTOTest {
	ItemDTO dummy;
	@BeforeEach
	void setUp() throws Exception {
		dummy = new ItemDTO("121212", "dummy", 12, 10);
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy = null;
	}

	@Test
	void testGetNetPrice() {
		assertEquals(12, dummy.getNetPrice(),"get net price Faild");
	}

	@Test
	void testGetName() {
		assertEquals("dummy", dummy.getName(),"get name Faild");
	}

	@Test
	void testGetId() {
		assertEquals("121212", dummy.getId(),"get id Faild");
	}

	@Test
	void testGetVAT() {
		assertEquals(10, dummy.getVAT(),"get Vat Faild");
	}

}
