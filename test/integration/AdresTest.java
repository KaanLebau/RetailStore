package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdresTest {
	Address dummy;
	
	
	@BeforeEach
	void setUp() throws Exception {
		dummy = new Address("G�teborg","Andra l�ng", 12,424);
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy = null;
	}

	@Test
	void testAdresStringStringIntInt() {
		assertNotNull(dummy);
	}

	@Test
	void testGetCity() {
		Address dummy = new Address("G�teborg","Andra l�ng", 12,424);
		assertEquals("G�teborg", dummy.getCity());
	}

	@Test
	void testGetStreet() {
		Address dummy = new Address("G�teborg","Andra l�ng", 12,424);
		assertEquals("Andra l�ng", dummy.getStreet());
	}

	@Test
	void testGetBuildingsNumber() {
		Address dummy = new Address("G�teborg","Andra l�ng", 12,424);
		assertEquals(12, dummy.getBuildingsNumber());
		
	}

	@Test
	void testGetZipCode() {
		Address dummy = new Address("G�teborg","Andra l�ng", 12,424);
		assertEquals(424, dummy.getZipCode());
		
	}

}
