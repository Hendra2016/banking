package com.bank.slik.controller;

import com.bank.slik.dto.SlikRequest;
import com.bank.slik.dto.SlikResponse;
import com.bank.slik.service.AuditService;
import com.bank.slik.service.SlikIntegrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/slik")
@RequiredArgsConstructor
public class SlikController {

    private final SlikIntegrationService service;
    private final AuditService auditService;

    @PostMapping("/check")
    public Mono<SlikResponse> check(
            @Valid @RequestBody SlikRequest request) {

        return service.inquiry(UUID.randomUUID().toString(),request)
                .doOnSuccess(response ->
                        auditService.log(
                                null,
                                request,
                                response,
                                "SUCCESS"
                        )
                );
    }
}