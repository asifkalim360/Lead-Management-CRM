package com.crm.repository;

import com.crm.enums.LeadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.entity.Lead;

import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

    public boolean existsByEmail(String email);

    public List<Lead> findByStatus(LeadStatus status);

}
