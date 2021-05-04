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
		dummy = new Address("Göteborg","Andra lång", 12,424);
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
		Address dummy = new Address("Göteborg","Andra lång", 12,424);
		assertEquals("Göteborg", dummy.getCity());
	}

	@Test
	void testGetStreet() {
		Address dummy = new Address("Göteborg","Andra lång", 12,424);
		assertEquals("Andra lång", dummy.getStreet());
	}

	@Test
	void testGetBuildingsNumber() {
		Address dummy = new Address("Göteborg","Andra lång", 12,424);
		assertEquals(12, dummy.getBuildingsNumber());
		
	}

	@Test
	void testGetZipCode() {
		Address dummy = new Address("Göteborg","Andra lång", 12,424);
		assertEquals(424, dummy.getZipCode());
		
	}

}
