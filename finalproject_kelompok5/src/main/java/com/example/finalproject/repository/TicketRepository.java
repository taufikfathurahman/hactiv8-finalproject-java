package com.example.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.finalproject.models.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
