package com.fxmxracingteam.userservice.service;

import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserAsyncService {
    private final JmsTemplate jmsTemplate;
    private final String QUEUE_NAME = "myQueue"; // Remplacez par le nom de votre file d'attente

    public UserAsyncService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String message) {
        jmsTemplate.send(QUEUE_NAME, session -> {
            TextMessage textMessage = session.createTextMessage();
            return textMessage;
        });
    }
}
