package com.ticket.ticketservice.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Ticket {

	@Id
	@Column(name="ticket_id")
	private Long id;
	
	@Column
	private String description;
	
	@OneToMany(mappedBy="ticket", cascade=CascadeType.ALL)
	private List<Task> tasks;
	
	public Ticket() {
		//Do Nothing
	}
	
}
