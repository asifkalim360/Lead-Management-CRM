package com.crm.mapper;

import com.crm.dto.LeadRequestDto;
import com.crm.dto.LeadResponseDto;
import com.crm.entity.Lead;
import com.crm.enums.LeadStatus;

public class LeadMapper {

    public static Lead toEntity(LeadRequestDto dto) {
        Lead lead = new Lead();
        lead.setFullName(dto.getFullName());
        lead.setEmail(dto.getEmail());
        lead.setPhone(dto.getPhone());
        lead.setSource(dto.getSource());
        lead.setAssignedTo(dto.getAssignedTo());
        lead.setStatus(LeadStatus.NEW);
        return lead;
    }

    public static LeadResponseDto toDto(Lead lead) {
        LeadResponseDto dto = new LeadResponseDto();
        dto.setId(lead.getId());
        dto.setFullName(lead.getFullName());
        dto.setEmail(lead.getEmail());
        dto.setPhone(lead.getPhone());
        dto.setSource(lead.getSource());
        dto.setAssignedTo(lead.getAssignedTo());
        dto.setStatus(lead.getStatus().name());
        return dto;
    }
}