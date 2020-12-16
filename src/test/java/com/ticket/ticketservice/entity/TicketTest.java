package com.ticket.ticketservice.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TicketTest {

	private Ticket ticket;
	
	@Before
	public void init() {
		ticket = new Ticket();
	}
	
	@After
	public void destroy() {
		ticket = null;
	}
	
	@Test
	public void testId() {
		Long id = 1L;
		ticket.setId(id);
		assertEquals(id, ticket.getId());
	}
	
	@Test
	public void testDescription() {
		ticket.setDescription("test-description");
		assertEquals("test-description", ticket.getDescription());
	}
	
	@Test
	public void testTasks() {
		assertNull(ticket.getTasks());
		ticket.setTasks(Collections.singletonList(new Task()));
		assertFalse(ticket.getTasks().isEmpty());
		
	}
}
