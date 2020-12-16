package com.ticket.ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.ticketservice.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
