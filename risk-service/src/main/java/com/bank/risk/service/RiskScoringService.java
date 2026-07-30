package com.bank.risk.service;

import com.bank.common.event.dto.ApplicationStatus;
import com.bank.common.event.dto.ApplicationStatusEvent;
import com.bank.common.event.dto.ScoringCompletedEvent;
import com.bank.common.event.dto.SlikCompletedEvent;
import com.bank.risk.entity.RiskScore;
import com.bank.risk.kafka.ScoringCompletedProducer;
import com.bank.risk.repository.RiskScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskScoringService {

    private final RiskScoreRepository repository;
    private final ScoringCompletedProducer producer;

    public void calculate(
            SlikCompletedEvent event) {

        int score = 100;

        if (event.collectibility() >= 2) {
            score -= 40;
        }

        if (event.activeLoans() > 3) {
            score -= 20;
        }
        boolean isApproved = score >= 70;

        RiskScore riskScore =
                RiskScore.builder()
                        .applicationId(
                                event.applicationId())
                        .score(score)
                        .decision(isApproved ? "APPROVED" : "REJECTED")
                        .build();

        riskScore =  repository.save(riskScore);
        if(isApproved){
            producer.publish(
                    new ScoringCompletedEvent(
                            riskScore.getApplicationId(),
                            riskScore.getScore(),
                            riskScore.getDecision(),
                            "SCORING_COMPLETED"
                    )
            );
        }else {
            producer.failed(
                    new ApplicationStatusEvent(
                            event.applicationId(),
                            "RISK",
                            ApplicationStatus.REJECTED,
                            "Risk scoring failed"
                    )
            );
        }
    }
}