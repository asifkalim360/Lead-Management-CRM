package com.crm.controller;


import com.crm.dto.ApiResponse;
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
    public ApiResponse<LeadResponseDto> createLead(@Valid @RequestBody LeadRequestDto requestDto)
    {
        return ApiResponse.<LeadResponseDto>builder()
                .success(true)
                .message("Lead Created Successfully")
                .data(leadService.createLead(requestDto))
                .build();
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

    @PutMapping("/{id}")
    public LeadResponseDto updateLead(@PathVariable Long id, @RequestBody LeadRequestDto requestDto)
    {
        return leadService.updateLead(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public String deleteLead(Long id)
    {
        leadService.deleteLead(id);
        return "Lead deleted Successfully";
    }

    @GetMapping("/status/{status}")
    public List<LeadResponseDto> getByStatus(@PathVariable String status) {
        return leadService.getLeadsByStatus(status);
    }

}
