package com.fxmxracingteam.storeservice.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class StoreAsyncReceiver {
    @JmsListener(destination = "myQueue")
    public void receiveMessage(String message) {
        // Traitez votre message ici
        System.out.println("Received message: " + message);
    }
}
