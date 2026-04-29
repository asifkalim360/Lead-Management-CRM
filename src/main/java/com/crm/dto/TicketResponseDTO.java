package com.crm.dto;

import lombok.Data;

@Data
public class TicketResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private Long leadId;
}
