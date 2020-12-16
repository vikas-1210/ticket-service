package com.ticket.ticketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ticket.ticketservice.service.TaskService;

@RestController
@RequestMapping(path="/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@GetMapping(path = "/{taskId}", produces = "application/json")
	public ResponseEntity<String> getTask(@PathVariable("taskId") Long taskId) throws JsonProcessingException{
		return taskService.getTask(taskId);
	}
	
}
