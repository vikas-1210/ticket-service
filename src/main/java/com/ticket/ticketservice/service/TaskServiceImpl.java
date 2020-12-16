package com.ticket.ticketservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ticket.ticketservice.entity.Task;
import com.ticket.ticketservice.repository.TaskRepository;
import com.ticket.ticketservice.utility.TicketServiceUtility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public ResponseEntity<String> getTask(Long taskId) throws JsonProcessingException {
		log.info("Retrieving task detail for task id: %d", taskId);
		Optional<Task> optionalTask = taskRepository.findById(taskId);
		if(!optionalTask.isPresent()) {
			String message = "Unable to find task with task id: "+taskId;
			log.info(message);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		}
		String response = TicketServiceUtility.getObjectMapperInstance().writeValueAsString(optionalTask.get());
		log.info("Task details retrieved successfully for task id: %d", taskId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
