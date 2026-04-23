package com.crm.controller;


import com.crm.dto.LeadRequestDto;
import com.crm.dto.LeadResponseDto;
import com.crm.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {
    private final LeadService leadService;

    public LeadController(LeadService leadService)
    {
        this.leadService = leadService;
    }

    @PostMapping
    public LeadResponseDto createLead(@Valid @RequestBody LeadRequestDto requestDto)
    {
        return leadService.createLead(requestDto);
    }

    @GetMapping
    public List<LeadResponseDto> getAllLeads()
    {
        return leadService.getAllLeads();
    }

    @GetMapping("/{id}")
    public LeadResponseDto getLeadById(@PathVariable Long id)
    {
        return leadService.getLeadById(id);
    }
}
