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
import com.crm.service.AuditService;
import com.crm.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
    private final AuditService auditService;


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
        // Pehle check kar rahe hain ki DTO me assignedUserId aaya bhi hai ya nahi
        // Agar null hai → matlab user assign hi nahi karna | Agar value hai → tab hi aage ka process chalega
        // Safety check (null pointer se bachne ke liye)
        if (dto.getAssignedUserId() != null)
        {
            // DTO se userId liya aur Database me us user ko find kiya
            // Agar user mil gaya → OK  |  Agar nahi mila → exception throw ho jayega. (Matlab:Invalid userId allow nahi hai)
            User user = userRepository.findById(dto.getAssignedUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

            ticket.setAssignedUser(user);   // Ticket ko us user ke saath link kar diya (relation set) | DB me: assigned_user_id (foreign key) set ho jayegi
        }

        // (ticketRepository.save(ticket)) -> yahan pe Ticket ko database me save kar diya matlab JPA internally: INSERT query ko run karega -> aur ID generate karega.
        // Important: Ab saved.getId() use kar sakte hain (kyunki DB ne ID de di)
        Ticket saved = ticketRepository.save(ticket);

        // Yahan pe audit history save ho rahe hain.
        auditService.log(
                "CREATE",    // "CREATE" → action kya hua (new ticket bana)
                "Ticket",           // "Ticket" → kis entity pe hua
                saved.getId(),      // saved.getId() → kaunsa record (ID)
                "SYSTEM",           // "SYSTEM" → kisne kiya (abhi static, baad me user aayega)
                null,               // null → old value nahi hai (kyunki create me pehle kuch tha hi nahi)
                saved.toString()    // saved.toString() → new data kya hai (poora ticket object string me)
        );

        // Save ke baad: Entity ko DTO me convert kiya -> jisse Client ko clean response mil gaya.
        // Important: Kabhi bhi direct Entity return nahi karte chahye → Security + clean API design
        return mapper.toDto(saved);
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
        // Step 1: Existing ticket fetch
        Ticket existingTicket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket Not Found"));

        // Update se pehle ka poora data store kar liya.Ye audit ke liye important hai
        // Matlab: "change hone se pehle ticket kaisa tha"
        // Step 2: Old data ka copy (audit ke liye)
        Ticket oldCopy = Ticket.builder()
                .id(existingTicket.getId())
                .title(existingTicket.getTitle())
                .description(existingTicket.getDescription())
                .priority(existingTicket.getPriority())
                .status(existingTicket.getStatus())
                .build();

        // uske baad DTO se naye values leke existing ticket me set kiye. (Title update, Description update, Priority update)
        // Yahan pe actual data change ho raha hai.
        // Step 3: New values set karo
        existingTicket.setTitle(dto.getTitle());
        existingTicket.setDescription(dto.getDescription());
        existingTicket.setPriority(dto.getPriority());

        // Assign / Reassign User
        // Pehle check kar rahe hain ki DTO me assignedUserId aaya bhi hai ya nahi
        // Agar null hai → matlab user assign hi nahi karna | Agar value hai → tab hi aage ka process chalega
        // Safety check (null pointer se bachne ke liye)
        if (dto.getAssignedUserId() != null)
        {
            // DTO se userId liya aur Database me us user ko find kiya
            // Agar user mil gaya → OK  |  Agar nahi mila → exception throw ho jayega. (Matlab:Invalid userId allow nahi hai)
            User user = userRepository.findById(dto.getAssignedUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

            existingTicket.setAssignedUser(user);   // Ticket ko us user ke saath link kar diya (relation set) | DB me: assigned_user_id (foreign key) set ho jayegi
        }

        // Ye UPDATE query run karega (INSERT nahi)
        // Kyunki ticket ke paas already ID hai. updated me latest data aa gaya
        // 🔹 Step 4: Save (update)
        Ticket updatedTicket = ticketRepository.save(existingTicket);

        // Yahan humlog before vs after record kar raha hai:
        // Step 5: Audit log (OBJECT pass karo, String nahi)
        auditService.log(
                "UPDATE",
                "Ticket",
                updatedTicket.getId(),
                "SYSTEM",
                oldCopy,
                updatedTicket
        );
        // Step 6: Response return
        return mapper.toDto(updatedTicket);   // Updated entity ko DTO me convert karke client ko bhej diya
    }

    @Override
    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket Not Found"));

        // Delete se pehle ticket ka poora data store kar liya. Kyunki delete ke baad data DB me nahi milega
        // Matlab: "delete hone se pehle record kaisa tha"
        String oldValue = ticket.toString();

        // DB se record remove kar diya  |  Internally DELETE query run hoti hai  |  Ab ye ticket database me exist nahi karega.
        ticketRepository.delete(ticket);

        // Yahan delete ka record save ho raha hai:
        auditService.log(
                "DELETE",    //"DELETE" → action
                "Ticket",           //"Ticket" → entity
                id,                 //id → kaunsa record delete hua
                "SYSTEM",           //"SYSTEM" → kisne delete kiya
                oldValue,           //oldValue → delete se pehle kya data tha
                null                //null → new value nahi hai (kyunki delete ho gaya)
        );
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

    //PAGINATION.
    @Override
    public Page<TicketResponseDTO> getAllTicketsPagination(int page, int size, String sortBy, String direction)
    {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Ticket> ticketPage = ticketRepository.findAll(pageable);
        return ticketPage.map(mapper::toDto);
    }
}
