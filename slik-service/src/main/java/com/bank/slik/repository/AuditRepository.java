package com.bank.slik.repository;

import com.bank.slik.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository
        extends JpaRepository<AuditLog, Long> {
}