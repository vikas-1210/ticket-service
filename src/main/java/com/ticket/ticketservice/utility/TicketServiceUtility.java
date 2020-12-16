package com.ticket.ticketservice.utility;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class TicketServiceUtility {

	private static ObjectMapper om;
	
	private TicketServiceUtility() {}
	
	public static ObjectMapper getObjectMapperInstance() {
		if(null == om) {
			om = new ObjectMapper();
		}
		return om;
	}
	
}
