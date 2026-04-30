package com.crm.dto;

import com.crm.enums.TicketPriority;
import lombok.Data;

@Data
public class TicketRequestDTO {

    private String title;

    private String description;

    private Long leadId;

    private TicketPriority priority;

    private Long assignedUserId;

}
