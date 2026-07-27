package com.bank.slik.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestId;

    private String applicationId;

    private String serviceName;

    @Column(columnDefinition="TEXT")
    private String requestPayload;

    @Column(columnDefinition="TEXT")
    private String responsePayload;

    private String status;

    private LocalDateTime createdDate;
}