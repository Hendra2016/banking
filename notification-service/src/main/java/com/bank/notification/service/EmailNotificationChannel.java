package com.bank.notification.service;

import org.springframework.stereotype.Service;

@Service
public class EmailNotificationChannel
        implements NotificationChannel {

    @Override
    public void send(
            String recipient,
            String message) {

        System.out.println(
                "EMAIL -> "
                        + recipient
                        + " : "
                        + message);
    }
}