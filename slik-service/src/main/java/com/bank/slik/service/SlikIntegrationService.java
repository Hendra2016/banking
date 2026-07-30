package com.bank.slik.service;

import com.bank.common.event.dto.ApplicationStatus;
import com.bank.common.event.dto.ApplicationStatusEvent;
import com.bank.common.event.dto.SlikCompletedEvent;
import com.bank.slik.dto.SlikRequest;
import com.bank.slik.dto.SlikResponse;
import com.bank.slik.kafka.SlikCompletedProducer;
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
    private final SlikCompletedProducer producer;

    @TimeLimiter(name = "slik")
    @Retry(name = "slik")
    @CircuitBreaker(
            name = "slik",
            fallbackMethod = "fallback"
    )
    public Mono<SlikResponse> inquiry(String applicationId,
                                      SlikRequest request) {

        return slikWebClient
                .post()
                .uri("/mockup?applicationId={applicationId}", applicationId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SlikResponse.class)
                .doOnSuccess(response ->
                {
                    producer.publish(
                            new SlikCompletedEvent(
                                    applicationId,
                                    request.name(),
                                    response.result(),
                                    response.collectibility(),
                                    response.activeLoans(),
                                    "SLIK_COMPLETED"
                            )
                    );
                    auditService.log(
                            applicationId,
                            request,
                            response,
                            "SUCCESS"
                    );
                })
                .doOnError(error -> {
                    producer.failed(
                            new ApplicationStatusEvent(
                                    applicationId,
                                    "SLIK",
                                    ApplicationStatus.REJECTED,
                                    error.getMessage()
                            )
                    );
                    auditService.log(
                            applicationId,
                            request,
                            error.getMessage(),
                            "FAILED"
                    );
                });
    }

    public Mono<SlikResponse> mockup(
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
        producer.failed(
                new ApplicationStatusEvent(
                        applicationId,
                        "SLIK",
                        ApplicationStatus.REJECTED,
                        ex.getMessage()
                )
        );
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