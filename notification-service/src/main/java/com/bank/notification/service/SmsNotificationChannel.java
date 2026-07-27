package com.bank.notification.service;

import org.springframework.stereotype.Service;

@Service
public class SmsNotificationChannel
        implements NotificationChannel {

    @Override
    public void send(
            String recipient,
            String message) {

        System.out.println(
                "SMS -> "
                        + recipient
                        + " : "
                        + message);
    }
}