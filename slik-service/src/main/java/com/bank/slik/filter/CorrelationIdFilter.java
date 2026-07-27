package com.bank.slik.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CorrelationIdFilter implements WebFilter {

    public static final String HEADER = "X-Request-Id";

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            WebFilterChain chain) {

        String requestId = exchange
                .getRequest()
                .getHeaders()
                .getFirst(HEADER);

        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }

        exchange.getResponse()
                .getHeaders()
                .add(HEADER, requestId);

        MDC.put("requestId", requestId);

        return chain.filter(exchange)
                .doFinally(signal -> MDC.clear());
    }
}