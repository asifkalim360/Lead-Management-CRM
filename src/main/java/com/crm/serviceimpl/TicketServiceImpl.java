package com.crm.serviceimpl;

import com.crm.dto.TicketRequestDTO;
import com.crm.dto.TicketResponseDTO;
import com.crm.entity.Lead;
import com.crm.entity.Ticket;
import com.crm.entity.User;
import com.crm.enums.TicketPriority;
import com.crm.enums.TicketStatus;
import com.crm.exception.ResourceNotFoundException;
import com.crm.mapper.TicketMapper;
import com.crm.repository.LeadRepository;
import com.crm.repository.TicketRepository;
import com.crm.repository.UserRepository;
import com.crm.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// (@RequiredArgsConstructor) -> Lombok annotation hai:
// jitne bhi final variables hote hain unke liye yeh constructor automatically bana deta hai:(Matlab dependency injection ho raha hai constructor ke through)
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {   // Ye class TicketService interface ko implement kar rahi hai → Good practice (loose coupling + testability)

    // Yahan pe 3 dependencies inject ho rahi hain:
    // (final + @RequiredArgsConstructor) → eski wajah se Spring automatically dependencies inject karega
    private final LeadRepository leadRepository;        // leadRepository → Lead ko DB se lane ke liye
    private final TicketRepository ticketRepository;    // ticketRepository → Ticket ko DB me save/fetch karne ke liye
    private final TicketMapper mapper;                  // mapper → DTO ↔ Entity conversion ke liye
    private final UserRepository userRepository;

    @Override
    public TicketResponseDTO createTicket(TicketRequestDTO dto) {
        // Step by step: (dto.getLeadId()) → client ne jo leadId bheja hai  ||  findById() → DB me check karta hai.
        //Agar id mil gaya → toh Lead object mil gaya  ||  Agar id nahi mila → toh exception throw karega
        // Important: Invalid leadId pe system fail fast karta hai
        Lead lead = leadRepository.findById(dto.getLeadId()).orElseThrow(() -> new ResourceNotFoundException("Lead not found!"));

        // Mapper ka use karke: DTO ko Entity me convert kiya  ->  Fields jaise title, description, priority map ho gaye
        // Clean architecture: Controller → DTO  ||  Service → Entity  (Isliye mapping important hai)
        Ticket ticket = mapper.toEntity(dto);

        // Ticket ka relation Lead se hai (ManyToOne probably).
        // Agar ye nahi lagaya to DB me foreign key null aa sakti hai
        ticket.setLead(lead);

        // Jab ticket create hota hai → default status OPEN rahega.
        ticket.setStatus(TicketStatus.OPEN);

        // Assign User Logic
        if (dto.getAssignedUserId() != null) {
            User user = userRepository.findById(dto.getAssignedUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            ticket.setAssignedUser(user);
        }

        // (ticketRepository.save(ticket)) -> yahan pe Ticket ko database me save kar diya matlab JPA internally: INSERT query ko run karega -> aur ID generate karega.
        // Save ke baad: Entity ko DTO me convert kiya -> jisse Client ko clean response mil gaya.
        // Important: Kabhi bhi direct Entity return nahi karte chahye → Security + clean API design
        return mapper.toDto(ticketRepository.save(ticket));
    }

    @Override
    public List<TicketResponseDTO> getAllTicket()
    {
        return ticketRepository.findAll()   // DB se saare Ticket entities la raha hai
                .stream()   // List ko stream me convert kar diya || Stream ka matlab: ek pipeline jisme hum data pe operations laga sakte hain

                // .map() =>  Har element uthata hai (stream se) aur Lambda apply karta hai | aur Naya transformed stream banata hai
                // (ticket -> mapper.toDto(ticket) => // Ye ek lambda function hai jo ki "Har ek ticket le raha → aur usko mapper ke through DTO me convert karo"
                .map(ticket -> mapper.toDto(ticket))
                // .map(mapper::toDto)  // shortcyt hai ye eska(.map(ticket -> mapper.toDto(ticket)))
                .toList();  // Final stream ko List me convert karta hai

        // (NOTE :: Stream internally iterate karti hai, map har element ko transform karta hai, aur toList() us result ko list me convert karta hai.)

    }

    @Override
    public TicketResponseDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket Not Found"));
        return mapper.toDto(ticket);
    }

    @Override
    public TicketResponseDTO updateTicket(Long id, TicketRequestDTO dto) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket Not Found"));

        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());
        ticket.setPriority(dto.getPriority());

        // Assign / Reassign User
        if (dto.getAssignedUserId() != null) {
            User user = userRepository.findById(dto.getAssignedUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            ticket.setAssignedUser(user);
        }

        return mapper.toDto(ticketRepository.save(ticket));
    }

    @Override
    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket Not Found"));
        ticketRepository.delete(ticket);
    }

    @Override
    public List<TicketResponseDTO> getByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<TicketResponseDTO> getByPriority(TicketPriority priority) {
        return ticketRepository.findByPriority(priority)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
