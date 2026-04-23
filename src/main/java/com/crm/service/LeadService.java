package com.crm.service;


import com.crm.dto.LeadRequestDto;
import com.crm.dto.LeadResponseDto;

import java.util.List;

public interface LeadService {

    public LeadResponseDto createLead(LeadRequestDto requestDto);

    public List<LeadResponseDto> getAllLeads();

    public LeadResponseDto getLeadById(Long id);

    public LeadResponseDto updateLead(Long id, LeadRequestDto requestDto);

    public void deleteLead(Long id);

}