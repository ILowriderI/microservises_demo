package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Notification;
import org.example.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    public void send(Notification notification) {
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notification.getToCustomerId())
                        .toCustomerEmail(notification.getToCustomerEmail())
                        .sender("Yura")
                        .message(notification.getMessage())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
