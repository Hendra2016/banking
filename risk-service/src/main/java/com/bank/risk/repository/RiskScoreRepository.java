package com.bank.risk.repository;

import com.bank.risk.entity.RiskScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskScoreRepository
        extends JpaRepository<RiskScore, Long> {
}