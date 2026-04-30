package com.crm.service;

import com.crm.dto.TicketRequestDTO;
import com.crm.dto.TicketResponseDTO;
import com.crm.enums.TicketPriority;
import com.crm.enums.TicketStatus;

import java.util.List;

public interface TicketService {

    public TicketResponseDTO createTicket(TicketRequestDTO dto);

    public List<TicketResponseDTO> getAllTicket();

    public TicketResponseDTO getTicketById(Long id);

    public TicketResponseDTO updateTicket(Long id, TicketRequestDTO dto);

    public void deleteTicket(Long id);

    public List<TicketResponseDTO> getByStatus(TicketStatus status);

    public List<TicketResponseDTO> getByPriority(TicketPriority priority);

}

