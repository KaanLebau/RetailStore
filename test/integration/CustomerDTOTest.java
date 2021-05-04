package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerDTOTest {
	CustomerDTO dummy;

	
	@BeforeEach
	void setUp() throws Exception {
		dummy = new CustomerDTO("123", "kalle");
	}

	@AfterEach
	void tearDown() throws Exception {
		dummy = null;
	}
	@Test
	void testCustomerDTO() {
		CustomerDTO emptyDummy = new CustomerDTO();
		assertEquals(null, emptyDummy.getId(),"null customer id Faild");
		assertEquals(null, emptyDummy.getName(),"null customer name Faild");
	
	}

	@Test
	void testCustomerDTOCustomerDTO() {
		CustomerDTO result = new CustomerDTO(dummy);
		assertEquals(dummy, result, "customer constructor whith a customer Faild");
	}

	@Test
	void testGetName() {
		assertEquals("kalle", dummy.getName(),"get name Faild");
	}

	@Test
	void testGetId() {
		assertEquals("123", dummy.getId(),"get id Faild");
	}

}
