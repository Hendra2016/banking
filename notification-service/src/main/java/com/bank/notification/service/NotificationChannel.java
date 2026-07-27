package com.bank.notification.service;

public interface NotificationChannel {
    void send(
            String recipient,
            String message);
}