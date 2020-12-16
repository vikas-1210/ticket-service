package com.ticket.ticketservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.ticketservice.entity.Task;
import com.ticket.ticketservice.entity.Ticket;
import com.ticket.ticketservice.repository.TaskRepository;
import com.ticket.ticketservice.repository.TicketRepository;

@RunWith(SpringRunner.class)
public class TicketServiceImplTest {

	private ObjectMapper om = new ObjectMapper();
	
	@TestConfiguration
	static class TicketServiceImplTestContextConfiguration {
		@Bean
		public TicketService ticketService() {
			return new TicketServiceImpl();
		}
	}
	
	@Autowired
	private TicketService ticketService;
	
	@MockBean
	private TicketRepository ticketRepository;
	
	@MockBean
	private TaskRepository taskRepository;
	
	@Test
	public void testPostTicketRequestBody() throws IOException {
		assertEquals(HttpStatus.BAD_REQUEST, ticketService.postTicket(null).getStatusCode());
	}
	
	@Test
	public void testPostTicketNullTicketId() throws IOException {
		String requestBody = om.writeValueAsString(new Ticket());
		assertEquals(HttpStatus.BAD_REQUEST, ticketService.postTicket(requestBody).getStatusCode());
	}
	
	@Test
	public void testPostTicketNullTaskId() throws IOException {
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setTasks(Collections.singletonList(new Task()));
		String requestBody = om.writeValueAsString(ticket);
		assertEquals(HttpStatus.BAD_REQUEST, ticketService.postTicket(requestBody).getStatusCode());
	}
	
	@Test
	public void testPostTicketInvalidTicketId() throws IOException {
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
		String requestBody = om.writeValueAsString(ticket);
		assertEquals(HttpStatus.BAD_REQUEST, ticketService.postTicket(requestBody).getStatusCode());
	}
	
	@Test
	public void testPostTicketAlreadyPresentTaskId() throws IOException {
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		
		Task task = new Task();
		task.setId(1L);
		List<Task> taskList = new ArrayList<>();
		taskList.add(task);
	
		ticket.setTasks(taskList);
		
		when(taskRepository.findAllById(any())).thenReturn(taskList);
		String requestBody = om.writeValueAsString(ticket);
		assertEquals(HttpStatus.BAD_REQUEST, ticketService.postTicket(requestBody).getStatusCode());
	}
	
	@Test
	public void testPostTicketSuccess() throws IOException {
		Task task = new Task();
		task.setId(1L);
		task.setDescription("test-task-1");
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setTasks(Collections.singletonList(task));
		String requestBody = om.writeValueAsString(ticket);
		assertEquals(HttpStatus.CREATED, ticketService.postTicket(requestBody).getStatusCode());
	}

	@Test
	public void testPatchTicketRequestBody() throws IOException {
		assertEquals(HttpStatus.BAD_REQUEST, ticketService.patchTicket(null, 1L).getStatusCode());
	}
	
	@Test
	public void testPatchTicketInvalidTaskId() throws IOException {
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.empty());
		String requestBody = om.writeValueAsString(ticket);
		assertEquals(HttpStatus.NOT_FOUND, ticketService.patchTicket(requestBody, ticket.getId()).getStatusCode());
	}
	
	@Test
	public void testPatchTicketMissingTaskId() throws IOException {
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setDescription("test-ticket-1");
		
		Task task = new Task();
		task.setId(1L);
		List<Task> tasks = new ArrayList<>();
		tasks.add(task);
		
		ticket.setTasks(tasks);
		
		when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
		
		Ticket requestTicket = new Ticket();
		requestTicket.setId(1L);
		requestTicket.setTasks(Collections.singletonList(new Task()));
		
		String requestBody = om.writeValueAsString(requestTicket);
		assertEquals(HttpStatus.BAD_REQUEST, ticketService.patchTicket(requestBody, ticket.getId()).getStatusCode());
	}
	
	@Test
	public void testPatchTicketSuccess() throws IOException {
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setDescription("test-ticket-1");
		
		Task task1 = new Task();
		task1.setId(1L);
		task1.setDescription("test-task-1");
		List<Task> tasks = new ArrayList<>();
		tasks.add(task1);
		
		ticket.setTasks(tasks);
		
		when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
		
		Ticket requestTicket = new Ticket();
		requestTicket.setId(1L);
		requestTicket.setDescription("test-ticket-1-updated");
		
		Task task1Updated = new Task();
		task1Updated.setId(1L);
		task1Updated.setDescription("test-task-1-updated");
		Task task2 = new Task();
		task2.setId(2L);
		
		List<Task> requestTasks = new ArrayList<>();
		requestTasks.add(task1Updated);
		requestTasks.add(task2);
		
		requestTicket.setTasks(requestTasks);
		
		String requestBody = om.writeValueAsString(requestTicket);
		assertEquals(HttpStatus.OK, ticketService.patchTicket(requestBody, ticket.getId()).getStatusCode());
	}
	
	@Test
	public void testDeleteTicketInvalidTicketId() {
		when(ticketRepository.findById(100L)).thenReturn(Optional.empty());
		assertEquals(HttpStatus.NOT_FOUND, ticketService.deleteTicket(100L).getStatusCode());
	}
	
	@Test
	public void testDeleteTicketSuccess() {
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
		doNothing().when(ticketRepository).deleteById(ticket.getId());
		assertEquals(HttpStatus.OK, ticketService.deleteTicket(ticket.getId()).getStatusCode());
	}
}

