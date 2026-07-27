package com.bank.notification.service;

import org.springframework.stereotype.Service;

@Service
public class WhatsappNotificationChannel
        implements NotificationChannel {

    @Override
    public void send(
            String recipient,
            String message) {

        System.out.println(
                "WHATSAPP -> "
                        + recipient
                        + " : "
                        + message);
    }
}