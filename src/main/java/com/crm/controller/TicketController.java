package com.crm.controller;

import com.crm.dto.TicketRequestDTO;
import com.crm.dto.TicketResponseDTO;
import com.crm.enums.TicketPriority;
import com.crm.enums.TicketStatus;
import com.crm.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<?> createTicket(@Valid @RequestBody TicketRequestDTO dto)
    {
        return ResponseEntity.ok(ticketService.createTicket(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTicket()
    {
        return ResponseEntity.ok(ticketService.getAllTicket());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Long id)
    {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable Long id, @RequestBody TicketRequestDTO dto)
    {
        return ResponseEntity.ok(ticketService.updateTicket(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id)
    {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok("Ticket deleted Successfully");
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getByStatus(TicketStatus status)
    {
        return ResponseEntity.ok(ticketService.getByStatus(status));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<?> getByPriority(TicketPriority priority)
    {
        return ResponseEntity.ok(ticketService.getByPriority(priority));
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> getAllTicketsPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    )
    {
        return ResponseEntity.ok(ticketService.getAllTicketsPagination(page, size, sortBy, direction));
    }

}
