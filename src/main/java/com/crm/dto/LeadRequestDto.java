package com.crm.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LeadRequestDto {
	
	@NotBlank
	private String fullName; 
	
	@Email
	private String email; 
	
	@NotBlank
	private String phone; 
	
	private String source; 
	
	private String assignedTo;
	
}
