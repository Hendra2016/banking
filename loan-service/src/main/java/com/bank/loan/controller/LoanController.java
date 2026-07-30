package com.bank.loan.controller;

import com.bank.loan.dto.CreateApplicationRequest;
import com.bank.loan.entity.Loan;
import com.bank.loan.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Loan create(
            @Valid @RequestBody
            CreateApplicationRequest request,
            @RequestHeader("X-USER")
            String username,
            @RequestHeader("X-ROLE")
            String role) {

        if (!"CUSTOMER".equals(role)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN);
        }

        return service.create(
                username,
                request);
    }

    @GetMapping("/{id}")
    public Loan get(
            @PathVariable String id) {

        return service.findById(id);
    }

    @PostMapping("/{id}/submit")
    public Loan submit(
            @PathVariable String id,
            @RequestHeader("X-ROLE")
            String role) {
        if (!"CUSTOMER".equals(role)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN);
        }
        return service.submit(id);
    }
}