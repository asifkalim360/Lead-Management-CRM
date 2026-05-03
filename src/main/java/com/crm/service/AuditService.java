package com.crm.service;

public interface AuditService {

    void log(String action,
             String entityName,
             Long entityId,
             String performedBy,
             Object oldObj,
             Object newObj);

}
