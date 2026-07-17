package com.bank.customer.controller;

import com.bank.customer.dto.CustomerRequest;
import com.bank.customer.dto.CustomerResponse;
import com.bank.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public CustomerResponse create(
            @Valid @RequestBody
            CustomerRequest request) {

        return service.create(request);
    }

    @GetMapping("/{id}")
    public CustomerResponse get(
            @PathVariable Long id) {

        return service.findById(id);
    }
}