package com.crm.serviceimpl;

import com.crm.entity.AuditLog;
import com.crm.repository.AuditLogRepository;
import com.crm.service.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void log(String action,
                    String entityName,
                    Long entityId,
                    String performedBy,
                    Object oldObj,
                    Object newObj) {
        try {
            String oldValue = oldObj != null
                    ? objectMapper.writeValueAsString(oldObj)
                    : null;
            String newValue = newObj != null
                    ? objectMapper.writeValueAsString(newObj)
                    : null;

            AuditLog log = AuditLog.builder()
                    .action(action)
                    .entityName(entityName)
                    .entityId(entityId)
                    .performedBy(performedBy)
                    .oldValue(oldValue)
                    .newValue(newValue)
                    .timestamp(LocalDateTime.now())
                    .build();

            auditLogRepository.save(log);
        } catch (Exception e) {
            throw new RuntimeException("Error while logging audit", e);
        }
    }
}
