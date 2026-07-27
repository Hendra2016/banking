package com.bank.slik.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DltConsumer {

    @KafkaListener(
            topics = "loan-submitted-dlt",
            groupId = "slik-dlt"
    )
    public void consume(String payload) {
        log.error(
                "DLQ Message received: {}",
                payload
        );
    }
}
