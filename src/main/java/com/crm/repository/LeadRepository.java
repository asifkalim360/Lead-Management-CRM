package com.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.entity.Lead;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

    public boolean existsByEmail(String email);

}
