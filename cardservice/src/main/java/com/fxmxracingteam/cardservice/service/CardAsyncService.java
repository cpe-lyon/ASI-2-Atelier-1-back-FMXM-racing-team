package com.fxmxracingteam.cardservice.service;

import com.fxmxracingteam.cardlib.dto.CardTransactionDTO;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fxmxracingteam.cardlib.dto.CardDTO;

import jakarta.jms.ObjectMessage;

@Service
public class CardAsyncService {
	
    private final JmsTemplate jmsTemplate;
    private static final String QUEUE_NAME = "cardServiceQueue";

    public CardAsyncService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendAddCard(CardDTO cardDTO) {
        jmsTemplate.send(QUEUE_NAME, session -> {
            ObjectMessage oM = session.createObjectMessage(cardDTO);
            oM.setStringProperty("action", "add");
            return oM;
        });
    }
    
    public void sendDeleteCard(CardDTO cardDTO) {
        jmsTemplate.send(QUEUE_NAME, session -> {
            ObjectMessage oM = session.createObjectMessage(cardDTO);
            oM.setStringProperty("action", "delete");
            return oM;
        });
    }
    
    public void sendUpdateCard(CardDTO cardDTO, Integer transactionId) {
        jmsTemplate.send(QUEUE_NAME, session -> {
            ObjectMessage oM = session.createObjectMessage(new CardTransactionDTO(transactionId, cardDTO));
            oM.setStringProperty("action", "update");
            return oM;
        });
    }
}
