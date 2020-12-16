package com.ticket.ticketservice.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TaskTest {

	private Task task;
	
	@Before
	public void init() {
		task = new Task();
	}
	
	@After
	public void destroy() {
		task = null;
	}
	
	@Test
	public void testId() {
		Long id = 1L;
		task.setId(id);
		assertEquals(id, task.getId());
	}
	
	@Test
	public void testDescription() {
		task.setDescription("test-description");
		assertEquals("test-description", task.getDescription());
	}
	
	@Test
	public void testTicket() {
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setDescription("test-ticket-description");
		
		task.setTicket(ticket);
		
		assertEquals("test-ticket-description", task.getTicket().getDescription());
	}
	
	@Test
	public void testEquals() {
		Task task1 = new Task();
		task1.setId(1L);
		
		Task task2 = new Task();
		task2.setId(1L);
		
		assertTrue(task1.equals(task2));
		assertTrue(task1.equals(task1));
		assertFalse(task1.equals(null));
		assertFalse(task1.equals(new Ticket()));
	}
	
	@Test
	public void testHashCode() {

		Task task1 = new Task();
		task1.setId(1L);
		
		Task task2 = new Task();
		task2.setId(1L);
		
		assertTrue(task1.hashCode()==task1.hashCode());
	}
}
