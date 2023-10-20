package com.fxmxracingteam.cardservice.service;

import jakarta.jms.TextMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class CardAsyncService {
    private final JmsTemplate jmsTemplate;
    private final String QUEUE_NAME = "myQueue"; // Remplacez par le nom de votre file d'attente

    public CardAsyncService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String message) {
        jmsTemplate.send(QUEUE_NAME, session -> {
            TextMessage textMessage = session.createTextMessage();
            return textMessage;
        });
    }
}
