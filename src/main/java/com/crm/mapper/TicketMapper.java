package com.crm.mapper;

import com.crm.dto.TicketRequestDTO;
import com.crm.dto.TicketResponseDTO;
import com.crm.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public Ticket toEntity(TicketRequestDTO dto)
    {
        return Ticket.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .build();
    }

    public TicketResponseDTO toDto(Ticket ticket)
    {
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setId(ticket.getId());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setStatus(ticket.getStatus().name());
        dto.setPriority(ticket.getPriority().name());
        dto.setLeadId(ticket.getLead().getId());

        if(ticket.getAssignedUser() != null)
        {
            dto.setAssignedUserId(ticket.getAssignedUser().getId());
            dto.setAssignedUserName(ticket.getAssignedUser().getName());
        }
        return dto;
    }



}
