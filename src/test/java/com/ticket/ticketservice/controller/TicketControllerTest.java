package com.ticket.ticketservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.ticketservice.entity.Task;
import com.ticket.ticketservice.entity.Ticket;
import com.ticket.ticketservice.service.TicketService;

@RunWith(SpringRunner.class)
@WebMvcTest(TicketController.class)
public class TicketControllerTest {

	private ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TicketService ticketService;
	
	private String requestBody;
	
	@Before
	public void init() throws JsonProcessingException {
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setDescription("test-ticket-1");
		
		Task task = new Task();
		task.setId(1L);
		task.setDescription("test-task-1");
		
		ticket.setTasks(Collections.singletonList(task));
		
		requestBody = om.writeValueAsString(ticket);
	}
	
	@After
	public void destroy() {
		requestBody = null;
	}
	
	@Test
	public void testPostTicket() throws Exception {
		when(ticketService.postTicket(any())).thenReturn(new ResponseEntity<>("Success", HttpStatus.CREATED));
		mockMvc.perform(post("/tickets").content(requestBody).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void testPatchTicket() throws Exception {
		when(ticketService.patchTicket(any(), anyLong())).thenReturn(new ResponseEntity<>("Success", HttpStatus.OK));
		mockMvc.perform(patch("/tickets/1").content(requestBody).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteTicket() throws Exception {
		when(ticketService.deleteTicket(anyLong())).thenReturn(new ResponseEntity<>("Success", HttpStatus.OK));
		mockMvc.perform(delete("/tickets/1").content(requestBody).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
}
