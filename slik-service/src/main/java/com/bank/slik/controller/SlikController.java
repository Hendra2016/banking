package com.bank.slik.controller;

import com.bank.slik.dto.SlikRequest;
import com.bank.slik.dto.SlikResponse;
import com.bank.slik.service.SlikIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Tag(name = "SLIK API")
@RestController
@RequestMapping("/slik")
@RequiredArgsConstructor
public class SlikController {

    private final SlikIntegrationService service;

    @Operation(summary = "Check SLIK Information")
    @PostMapping("/check")
    public Mono<SlikResponse> check(
            @Valid @RequestBody SlikRequest request) {

        return service.inquiry(UUID.randomUUID().toString(),request);
    }
}