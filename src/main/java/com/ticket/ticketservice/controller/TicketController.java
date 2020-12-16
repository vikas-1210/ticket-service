package com.ticket.ticketservice.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.ticketservice.service.TicketService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<String> postTicket(@RequestBody String requestBody) throws IOException{
		return ticketService.postTicket(requestBody);
	}
	
	@PatchMapping(path="/{ticketId}", consumes = "application/json")
	public ResponseEntity<String> patchTicket(@RequestBody String requestBody, @PathVariable("ticketId") Long ticketId) throws IOException{
		return ticketService.patchTicket(requestBody, ticketId);
	}
	
	@DeleteMapping("/{ticketId}")
	public ResponseEntity<String> deleteTicket(@PathVariable("ticketId") Long ticketId){
		return ticketService.deleteTicket(ticketId);
	}
}
