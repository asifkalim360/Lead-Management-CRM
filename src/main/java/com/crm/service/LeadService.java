package com.crm.service;


import com.crm.dto.LeadRequestDto;
import com.crm.dto.LeadResponseDto;

import java.util.List;

public interface LeadService {

    LeadResponseDto createLead(LeadRequestDto requestDto);

    List<LeadResponseDto> getAllLeads();

    LeadResponseDto getLeadById(Long id);


}