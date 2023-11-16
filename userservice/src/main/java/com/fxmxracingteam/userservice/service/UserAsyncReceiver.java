package com.fxmxracingteam.userservice.service;

import com.fxmxracingteam.userlib.dto.UserDTO;
import com.fxmxracingteam.userlib.dto.UserTransactionDTO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class UserAsyncReceiver {
    private final UserService userService;
    private final String QUEUE_NAME = "userServiceQueue";

    public UserAsyncReceiver(UserService userService) {
        this.userService = userService;
    }

    @JmsListener(destination = QUEUE_NAME, selector = "action = 'CREATE'")
    public void receiveCreateUserDTO(UserDTO userDTO) {
        userService.addUser(userDTO, false);
    }

    @JmsListener(destination = QUEUE_NAME, selector = "action = 'UPDATE'")
    public void receiveUpdateUserDTO(UserTransactionDTO userTransactionDTO) {
        userService.updateUser(userTransactionDTO.getUserDTO(), false, userTransactionDTO.getTransactionId());
    }

    @JmsListener(destination = QUEUE_NAME, selector = "action = 'DELETE'")
    public void receiveCreateUserDTO(String id) {
        userService.deleteUser(id, false);
    }
}
