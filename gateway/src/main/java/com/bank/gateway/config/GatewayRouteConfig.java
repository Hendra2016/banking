package com.bank.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouteConfig {

    @Bean
    RouteLocator customRoutes(
            RouteLocatorBuilder builder) {

        return builder.routes()
                .route("customer-service",
                        r -> r.path("/customers/**")
                              .uri("http://localhost:8081"))
                .build();
    }
}