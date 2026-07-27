package com.bank.slik.service;

import com.bank.slik.entity.AuditLog;
import com.bank.slik.repository.AuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository repository;
    private final ObjectMapper objectMapper;

    public void log(
            String applicationId,
            Object request,
            Object response,
            String status) {

        try {

            AuditLog audit = AuditLog.builder()
                    .requestId(UUID.randomUUID().toString())
                    .applicationId(applicationId)
                    .serviceName("SLIK")
                    .requestPayload(
                            objectMapper.writeValueAsString(request)
                    )
                    .responsePayload(
                            objectMapper.writeValueAsString(response)
                    )
                    .status(status)
                    .createdDate(LocalDateTime.now())
                    .build();

            repository.save(audit);

        } catch (Exception ignored) {
        }
    }
}