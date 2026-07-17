package com.bank.customer.service;

import com.bank.customer.dto.CustomerRequest;
import com.bank.customer.dto.CustomerResponse;
import com.bank.customer.entity.Customer;
import com.bank.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerResponse create(
            CustomerRequest request) {

        Customer customer = Customer.builder()
                .nik(request.nik())
                .name(request.name())
                .phone(request.phone())
                .build();

        Customer saved =
                repository.save(customer);

        return new CustomerResponse(
                saved.getId(),
                saved.getNik(),
                saved.getName(),
                saved.getPhone()
        );
    }

    public CustomerResponse findById(Long id) {

        Customer customer =
                repository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Customer not found"));

        return new CustomerResponse(
                customer.getId(),
                customer.getNik(),
                customer.getName(),
                customer.getPhone()
        );
    }
}