package com.ticket.ticketservice.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.ticketservice.entity.Task;
import com.ticket.ticketservice.service.TaskService;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

	private ObjectMapper om = new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TaskService taskService;
	
	@Test
	public void testGetTask() throws Exception {
		Task task = new Task();
		task.setId(1L);
		String response = om.writeValueAsString(task);
		when(taskService.getTask(any())).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
		mockMvc.perform(get("/tasks/123"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString(response)));
	}
	
}
