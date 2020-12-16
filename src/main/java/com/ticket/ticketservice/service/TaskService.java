package com.ticket.ticketservice.service;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface TaskService {

	public ResponseEntity<String> getTask(Long taskId) throws JsonProcessingException;
	
}
