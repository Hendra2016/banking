package com.bank.slik.service;

import com.bank.slik.dto.CustomerResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CustomerClient {

    private final WebClient customerWebClient;

    public CustomerClient(
            @Qualifier("customerWebClient")
            WebClient customerWebClient) {

        this.customerWebClient = customerWebClient;
    }

    public CustomerResponse getCustomer(
            Long customerId) {

        return customerWebClient
                .get()
                .uri("/customers/{id}", customerId)
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .block();
    }
}
