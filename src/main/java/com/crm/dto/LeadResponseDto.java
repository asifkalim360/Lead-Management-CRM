package com.crm.dto;

import lombok.Data;

@Data
public class LeadResponseDto {
	
	private Long id; 
	
	private String fullName; 
	
	private String email; 
	
	private String phone; 
	
	private String source; 
	
	private String status; 
	
	private String assignedTo; 
	
}
