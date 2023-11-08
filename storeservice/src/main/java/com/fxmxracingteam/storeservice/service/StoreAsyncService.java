package com.fxmxracingteam.storeservice.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fxmxracingteam.storelib.dto.StoreTransactionDTO;

import jakarta.jms.ObjectMessage;

@Service
public class StoreAsyncService {
    private final JmsTemplate jmsTemplate;
    private static final String QUEUE_NAME = "storeServiceQueue"; // Remplacez par le nom de votre file d'attente

    public StoreAsyncService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendStoreTransaction(StoreTransactionDTO storeTransactionDTO) {
        jmsTemplate.send(QUEUE_NAME, session -> {
            ObjectMessage oM = session.createObjectMessage(storeTransactionDTO);
            return oM;
        });
    }

}
