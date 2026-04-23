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

    @Override
    public LeadResponseDto updateLead(Long id, LeadRequestDto requestDto) {
        Lead existingLead = leadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        existingLead.setFullName(requestDto.getFullName());
        existingLead.setEmail(requestDto.getEmail());
        existingLead.setPhone(requestDto.getPhone());
        existingLead.setSource(requestDto.getSource());
        existingLead.setAssignedTo(requestDto.getAssignedTo());

        Lead updatedLead = leadRepository.save(existingLead);
        return LeadMapper.toDto(updatedLead);
    }

    @Override
    public void deleteLead(Long id) {
        if(leadRepository.existsById(id))
        {
            throw new RuntimeException("Lead not found");
        }
        leadRepository.deleteById(id);
    }
}
