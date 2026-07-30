package com.bank.loan.entity;

import com.bank.common.event.dto.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loan_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String applicationId;

    private String customerId;

    private Double amount;

    private Integer tenure;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}