package com.bank.risk.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "risk_score")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskScore {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String scoreId;

    private String applicationId;

    private Integer score;

    private String decision;
}