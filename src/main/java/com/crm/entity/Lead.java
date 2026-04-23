package com.crm.entity;

import com.crm.enums.LeadStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(name = "leads") 
@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lead {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String fullName; 
	
	private String email; 
	
	private String phone; 
	
	private String source; 
	
	@Enumerated(EnumType.STRING)
	private LeadStatus status; 
	
	private String assignedTo; 

}