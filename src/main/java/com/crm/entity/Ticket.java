package com.crm.entity;

import com.crm.enums.TicketPriority;
import com.crm.enums.TicketStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;      // OPEN, IN_PROGRESS, CLOSED.
    @Enumerated(EnumType.STRING)
    private TicketPriority priority;    // LOW, MEDIUM, HIGH.

 //    private LocalDateTime createdAt;
 //    private LocalDateTime updatedAt;

    // Logic: Ek Lead → multiple Tickets ho sakte hain -> Isliye ManyToOne
    // @ManyToOne → ek lead ke multiple tickets ho sakte hain
    // @JoinColumn → DB me foreign key banega (lead_id)
    @ManyToOne      // MANY tickets belong to ONE lead
    @JoinColumn(name = "lead_id")
    @JsonBackReference
    private Lead lead;




 //    @ManyToOne
 //    @JoinColumn(name = "assigned_to")
 //    private User user;

}

 // Ticket Entity (Core Feature)
 // Jab lead convert hota hai → Ticket create hota hai
 // Ticket = Issue / Request / Work item

