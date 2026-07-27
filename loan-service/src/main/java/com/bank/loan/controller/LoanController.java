package com.bank.loan.controller;

import com.bank.loan.dto.CreateApplicationRequest;
import com.bank.loan.entity.Loan;
import com.bank.loan.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Loan create(
            @Valid @RequestBody
            CreateApplicationRequest request) {

        return service.create(request);
    }

    @GetMapping("/{id}")
    public Loan get(
            @PathVariable Long id) {

        return service.findById(id);
    }

    @PostMapping("/{id}/submit")
    public Loan submit(
            @PathVariable Long id) {

        return service.submit(id);
    }
}