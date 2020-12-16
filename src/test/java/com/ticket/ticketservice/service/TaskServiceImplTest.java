package com.ticket.ticketservice.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ticket.ticketservice.entity.Task;
import com.ticket.ticketservice.entity.Ticket;
import com.ticket.ticketservice.repository.TaskRepository;

@RunWith(SpringRunner.class)
public class TaskServiceImplTest {

	@TestConfiguration
	static class TaskServiceImplTestContextConfiguration {
		@Bean
		public TaskService taskService() {
			return new TaskServiceImpl();
		}
	}
	
	@Autowired
	private TaskService taskService;
	
	@MockBean
	private TaskRepository taskRepository;
	
	@Test
	public void testGetTaskWithInvalidTaskId() throws JsonProcessingException {
		when(taskRepository.findById(1L)).thenReturn(Optional.empty());
		assertEquals(HttpStatus.NOT_FOUND, taskService.getTask(1L).getStatusCode());
	}
	
	@Test
	public void testGetTaskSuccess() throws JsonProcessingException {
		Task task = new Task();
		task.setId(1L);
		
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setTasks(Collections.singletonList(task));
		
		task.setTicket(ticket);
		
		when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
		
		assertEquals(HttpStatus.OK, taskService.getTask(task.getId()).getStatusCode());
	}
}
