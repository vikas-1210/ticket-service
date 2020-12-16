package com.ticket.ticketservice.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

public interface TicketService {

	public ResponseEntity<String> postTicket(String requestBody) throws IOException;
	
	public ResponseEntity<String> patchTicket(String requestBody, Long ticketId) throws IOException;
	
	public ResponseEntity<String> deleteTicket(Long ticketId);
	
}
