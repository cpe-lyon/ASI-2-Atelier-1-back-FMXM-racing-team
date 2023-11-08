package com.fxmxracingteam.storeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fxmxracingteam.storelib.dto.StoreTransactionDTO;
import com.fxmxracingteam.storeservice.jpa.StoreTransactionJPA;
import com.fxmxracingteam.storeservice.mapper.StoreTransactionMapper;

@Component
public class StoreAsyncReceiver {
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	StoreTransactionMapper storeTransactionMapper;

	@JmsListener(destination = "storeServiceQueue")
    public void receiveStoreTransaction(StoreTransactionDTO storeTransactionDTO) {
		StoreTransactionJPA storeTransactionJPA = storeTransactionMapper.toStoreTransactionJPA(storeTransactionDTO);
        storeService.saveTransaction(storeTransactionJPA);
    }
	
}
