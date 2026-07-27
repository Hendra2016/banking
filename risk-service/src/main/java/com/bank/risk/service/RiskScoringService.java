package com.bank.risk.service;

import com.bank.risk.entity.RiskScore;
import com.bank.risk.event.SlikCompletedEvent;
import com.bank.risk.repository.RiskScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskScoringService {

    private final RiskScoreRepository repository;

    public RiskScore calculate(
            SlikCompletedEvent event) {

        int score = 100;

        if (event.collectibility() >= 2) {
            score -= 40;
        }

        if (event.activeLoans() > 3) {
            score -= 20;
        }

        String decision =
                score >= 70
                        ? "APPROVED"
                        : "REJECTED";

        RiskScore riskScore =
                RiskScore.builder()
                        .applicationId(
                                event.applicationId())
                        .score(score)
                        .decision(decision)
                        .build();

        return repository.save(riskScore);
    }
}