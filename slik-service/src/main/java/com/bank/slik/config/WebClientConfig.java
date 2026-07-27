package com.bank.slik.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

        @Bean
        public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
        }

    @Value("${slik.base-url}")
    private String slikUrl;

    @Bean
    public WebClient slikWebClient(
            WebClient.Builder builder) {

        return builder
                .baseUrl(slikUrl)
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }
}