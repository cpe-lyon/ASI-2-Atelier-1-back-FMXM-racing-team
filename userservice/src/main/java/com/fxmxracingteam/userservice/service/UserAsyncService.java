package com.fxmxracingteam.userservice.service;

import com.fxmxracingteam.userlib.dto.UserDTO;
import com.fxmxracingteam.userlib.dto.UserTransactionDTO;
import jakarta.jms.ObjectMessage;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAsyncService {
    private final JmsTemplate jmsTemplate;
    private final String QUEUE_NAME = "userServiceQueue";

    public UserAsyncService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void createUserDTO(UserDTO userDTO) {
        try {
            jmsTemplate.send(QUEUE_NAME, session -> {
                ObjectMessage objectMessage = session.createObjectMessage();
                objectMessage.setObject(userDTO);
                objectMessage.setStringProperty("action", "CREATE");
                return objectMessage;
            });
        } catch (JmsException e) {
            log.info(e.getMessage());
        }
    }

    public void updateUserDTO(UserDTO userDTO, Integer transactionId) {
        try {
            jmsTemplate.send(QUEUE_NAME, session -> {
                ObjectMessage objectMessage = session.createObjectMessage();
                objectMessage.setObject(new UserTransactionDTO(transactionId, userDTO));
                return objectMessage;
            });
        } catch (JmsException e) {
            log.error(e.getMessage());
        }
    }

    public void deleteUser(String id) {
        try {
            jmsTemplate.send(QUEUE_NAME, session -> {
                TextMessage textMessage = session.createTextMessage(id);
                textMessage.setStringProperty("action", "DELETE");
                return textMessage;
            });
        } catch (JmsException e) {
            log.error(e.getMessage());
        }
    }
}
