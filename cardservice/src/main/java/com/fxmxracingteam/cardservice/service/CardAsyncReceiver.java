package com.fxmxracingteam.cardservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fxmxracingteam.cardlib.dto.CardDTO;
import com.fxmxracingteam.cardservice.jpa.CardJPA;
import com.fxmxracingteam.cardservice.mapper.CardMapper;

@Component
public class CardAsyncReceiver {
	
	private static final Boolean ASYNC = false;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardMapper cardMapper;
	
	@JmsListener(destination = "cardServiceQueue", selector = "action = 'add'")
    public void receiveAddCard(CardDTO cardDTO) {
		CardJPA cardModel = cardMapper.toCardJPA(cardDTO);
        cardService.addCard(cardModel, ASYNC);
    }
	
	@JmsListener(destination = "cardServiceQueue", selector = "action = 'delete'")
    public void receiveDeleteCard(CardDTO cardDTO) {
		CardJPA cardModel = cardMapper.toCardJPA(cardDTO);
        cardService.deleteCard(null, cardModel, ASYNC);
    }
	
	@JmsListener(destination = "cardServiceQueue", selector = "action = 'update'")
    public void receiveUpdateCard(CardDTO cardDTO) {
		CardJPA cardModel = cardMapper.toCardJPA(cardDTO);
        cardService.updateCard(cardModel, ASYNC);
    }
}
