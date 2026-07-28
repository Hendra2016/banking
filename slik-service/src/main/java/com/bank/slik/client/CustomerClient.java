package com.bank.slik.client;

import com.bank.slik.dto.CustomerResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerClient {

    private final WebClient customerWebClient;

    @Retry(name = "slik")
    @CircuitBreaker(
            name = "slik",
            fallbackMethod = "fallbackCustomer")
    public CustomerResponse getCustomer(Long customerId) {
        return customerWebClient
                .get()
                .uri("/customers/{id}", customerId)
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .block();
    }

    public CustomerResponse fallbackCustomer(
            Long customerId,
            Exception ex) {

        log.error(
                "Customer service unavailable for customer {}",
                customerId,
                ex);

        CustomerResponse response = new CustomerResponse();

        response.setId(customerId);
        response.setName("UNKNOWN CUSTOMER");

        return response;
    }
}
