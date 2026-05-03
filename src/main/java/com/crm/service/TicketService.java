package com.crm.service;

import com.crm.dto.TicketRequestDTO;
import com.crm.dto.TicketResponseDTO;
import com.crm.entity.Ticket;
import com.crm.enums.TicketPriority;
import com.crm.enums.TicketStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TicketService {

    public TicketResponseDTO createTicket(TicketRequestDTO dto);

    public List<TicketResponseDTO> getAllTicket();    // yahan nicheline me List ko Page kiya hai kyunki pagination use ho raha hai

    //PAGINATION METHOD.
    public Page<TicketResponseDTO> getAllTicketsPagination(int page, int size, String sortBy, String direction);

    public TicketResponseDTO getTicketById(Long id);

    public TicketResponseDTO updateTicket(Long id, TicketRequestDTO dto);

    public void deleteTicket(Long id);

    public List<TicketResponseDTO> getByStatus(TicketStatus status);

    public List<TicketResponseDTO> getByPriority(TicketPriority priority);

    public List<TicketResponseDTO> findByPriorityAndStatus(TicketPriority priority, TicketStatus status);

}

