package com.crm.serviceimpl;

import com.crm.dto.LeadRequestDto;
import com.crm.dto.LeadResponseDto;
import com.crm.entity.Lead;
import com.crm.mapper.LeadMapper;
import com.crm.repository.LeadRepository;
import com.crm.service.LeadService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;

    public LeadServiceImpl(LeadRepository leadRepository)
    {
        this.leadRepository = leadRepository;
    }

    @Override
    public LeadResponseDto createLead(LeadRequestDto requestDto) {
        Lead lead = LeadMapper.toEntity(requestDto);
        Lead savedLead = leadRepository.save(lead);
        return LeadMapper.toDto(savedLead);
    }

    @Override
    public List<LeadResponseDto> getAllLeads() {
        return leadRepository.findAll()
                .stream()
                .map(LeadMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeadResponseDto getLeadById(Long id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead not Found"));
        return LeadMapper.toDto(lead);
    }
}
