// Overall ye class kya hai?
// Ye AuditLog entity hai | Iska kaam hai: system me hone wale changes ka record rakhna (history / tracking)
// Example: Ticket create hua, Ticket update hua, Ticket delete hua. Sab yahan store hoga

package com.crm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity                     // Ye batata hai ki ye class database table banegi.
@Table(name="audit_logs")   // Table ka naam audit_logs hoga DB me
@Getter                     // Lombok automatically getter & setter methods bana deta hai
@Setter
@NoArgsConstructor          // @NoArgsConstructor → empty constructor
@AllArgsConstructor         // @AllArgsConstructor → sab fields wala constructor
@Builder                    // @Builder → object ko clean tareeke se create karne ke liye
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // Ye primary key hai: Auto-increment hoga (DB khud value dega)

    private String action;          // Konsa action hua: (CREATE, UPDATE, DELETE)
    private String entityName;      // Kis entity pe action hua: (Ticket, Lead)
    private Long entityId;          // Kis record pe action hua hai: (uska ID)

    private String performedBy;     // Kis user ne action kiya (simple username)

    @Column(columnDefinition = "TEXT")
    private String oldValue;        // Update/Delete se pehle ka data. TEXT use kiya kyunki data bada ho sakta hai

    @Column(columnDefinition = "TEXT")
    private String newValue;        // Update ke baad ka data

    private LocalDateTime timestamp;// Action kab hua (date + time)
}

// AuditLog ek history table hai jo batata hai kisne, kya change kiya, kab kiya, aur pehle kya tha / baad me kya hua

