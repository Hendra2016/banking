package com.bank.slik.service;

import com.bank.slik.dto.SlikRequest;
import com.bank.slik.dto.SlikResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SlikIntegrationService {

    private final WebClient slikWebClient;
    private final AuditService auditService;

    @TimeLimiter(name = "slik")
    @Retry(name = "slik")
    @CircuitBreaker(
            name = "slik",
            fallbackMethod = "fallback"
    )
//    public Mono<SlikResponse> inquiry(String applicationId,
//                                      SlikRequest request) {
//
//        return slikWebClient
//                .post()
//                .uri("/inquiry")
//                .bodyValue(request)
//                .retrieve()
//                .bodyToMono(SlikResponse.class)
//
//                .doOnSuccess(response ->
//                        auditService.log(
//                                applicationId,
//                                request,
//                                response,
//                                "SUCCESS"
//                        ))
//
//                .doOnError(error ->
//                        auditService.log(
//                                applicationId,
//                                request,
//                                error.getMessage(),
//                                "FAILED"
//                        ));
//    }
//TO-DO hardcoded first, later will be dynamic
    public Mono<SlikResponse> inquiry(
            String applicationId,
            SlikRequest request) {

        return Mono.just(
                new SlikResponse(
                        "GOOD",
                        1,
                        0
                )
        );
    }

    private Mono<SlikResponse> fallback(
            String applicationId,
            SlikRequest request,
            Exception ex) {

        auditService.log(
                applicationId,
                request,
                ex.getMessage(),
                "FALLBACK"
        );

        return Mono.just(
                SlikResponse.pending()
        );
    }
}