package com.crm.repository;

import com.crm.entity.Ticket;
import com.crm.enums.TicketPriority;
import com.crm.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    public List<Ticket> findByStatus(TicketStatus status);

    public List<Ticket> findByPriority(TicketPriority priority);

}