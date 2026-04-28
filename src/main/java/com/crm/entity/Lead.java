package com.crm.entity;

import com.crm.enums.LeadStatus;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    // Important Concepts: mappedBy = "lead" -> batata hai ki relation Ticket side pe control ho raha hai.
    // cascade = ALL -> Lead delete → Tickets auto delete
    // orphanRemoval = true -> Agar ticket list se remove hua → DB se delete
    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference       // JsonManagedReference / JsonBackReference = infinite loop fix (very important interview point)
    private List<Ticket> tickets;


}