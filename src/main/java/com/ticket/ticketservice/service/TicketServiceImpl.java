package com.ticket.ticketservice.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ticket.ticketservice.entity.Task;
import com.ticket.ticketservice.entity.Ticket;
import com.ticket.ticketservice.repository.TaskRepository;
import com.ticket.ticketservice.repository.TicketRepository;
import com.ticket.ticketservice.utility.TicketServiceUtility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public ResponseEntity<String> postTicket(String requestBody) throws IOException {
		log.info("Adding ticket");
		if(!StringUtils.hasLength(requestBody)) {
			String response = "Request body cannot be empty";
			log.info(response);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		Ticket ticket = TicketServiceUtility.getObjectMapperInstance().readValue(requestBody, Ticket.class);
		if(null==ticket.getId()) {
			String response = "Id detail missing for Task";
			log.info(response);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		boolean isTaskIdMissing = false;
		if(null != ticket.getTasks()) {
			for(Task task:ticket.getTasks()) {
				if(null==task.getId()) {
					isTaskIdMissing = true;
					break;
				}
			}
		}

		if(isTaskIdMissing) {
			String response = "Id not available for some task";
			log.info(response);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		Optional<Ticket> optionalTicket = ticketRepository.findById(ticket.getId());
		if(optionalTicket.isPresent()) {
			String response = "Ticket detail already present with ticket id: "+optionalTicket.get().getId();
			log.info(response);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if(null!=ticket.getTasks()) {
			List<Task> taskList = taskRepository.findAllById(ticket.getTasks().stream().map(e->e.getId()).collect(Collectors.toList()));
			if(!taskList.isEmpty()) {
				String taskIds = taskList.stream().map(e-> ""+e.getId()).collect(Collectors.joining(", "));
				String response = "Task Ids: "+taskIds+" are already present";
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			
			ticket.getTasks().stream().forEach(e->e.setTicket(ticket));
		}
		
		ticketRepository.save(ticket);
		log.info("Ticket added successfully with id: %d", ticket.getId());
		return new ResponseEntity<>("Ticket details saved successfully", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> patchTicket(String requestBody, Long ticketId) throws IOException{
		log.info("Updating ticket details");

		if(!StringUtils.hasText(requestBody)) {
			log.info("Request body cannot be empty");
			return new ResponseEntity<>("Request body cannot be empty", HttpStatus.BAD_REQUEST);
		}

		Ticket tempTicket = TicketServiceUtility.getObjectMapperInstance().readValue(requestBody.getBytes(), Ticket.class);

		Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
		if(!optionalTicket.isPresent()) {
			log.info("Unable to find ticket with ticket id: "+tempTicket.getId());
			return new ResponseEntity<>("Unable to find ticket with ticket id: "+tempTicket.getId(), HttpStatus.NOT_FOUND);
		}

		Ticket ticket = optionalTicket.get();
		ticket.setDescription(tempTicket.getDescription());

		for(Task task: tempTicket.getTasks()) {
			// equals and hashCode method of Task class has been overridden to consider task id as determinant
			if(ticket.getTasks().contains(task)) {
				log.info("Updating task detail for task id: "+ task.getId());
				int elementIndex = ticket.getTasks().indexOf(task);
				ticket.getTasks().get(elementIndex).setDescription(task.getDescription());
			}else {
				if(null ==task.getId()) {
					log.info("Id attribute missing for some task");
					return new ResponseEntity<>("Id attribute missing for some task", HttpStatus.BAD_REQUEST);
				}
				log.info("Adding task detail for task id: "+ task.getId());
				task.setTicket(ticket);
				ticket.getTasks().add(task);
			}
		}
		ticketRepository.save(ticket);
		log.info("Ticket details updated successfully");
		return new ResponseEntity<>("Ticket details updated successfully", HttpStatus.OK);
	}

	public ResponseEntity<String> deleteTicket(Long ticketId) {
		log.info("Deleting ticket with ticket id: "+ticketId);

		Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
		if(!optionalTicket.isPresent()) {
			log.info("Unable to find ticket with ticket id: "+ticketId);
			return new ResponseEntity<>("Ticket details not avialable with ticket id: "+ticketId, HttpStatus.NOT_FOUND);
		}

		ticketRepository.deleteById(ticketId);

		log.info("Deleted ticket with ticket id: "+ticketId);
		return new ResponseEntity<>("Deleted ticket with ticket id: "+ticketId, HttpStatus.OK);
	}

}
