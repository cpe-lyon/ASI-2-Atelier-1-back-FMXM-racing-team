package com.fxmxracingteam.cardservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.fxmxracingteam.cardlib.dto.CardDTO;
import com.fxmxracingteam.cardservice.jpa.CardJPA;
import com.fxmxracingteam.cardservice.mapper.CardMapper;
import com.fxmxracingteam.cardservice.service.CardService;

@CrossOrigin
@RestController
public class CardRestController {
	
	private static final Boolean ASYNC = true;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardMapper cardMapper;
	
	@RequestMapping(method=RequestMethod.GET, value="/cards")
	private List<CardDTO> getAllCards() {
		List<CardDTO> cLightList=new ArrayList<>();
		for(CardJPA c:cardService.getAllCardModel()){
			CardDTO cLight = cardMapper.toCardDTO(c);
			cLightList.add(cLight);
		}
		return cLightList;

	}
	
	@RequestMapping(method=RequestMethod.GET, value="/cards/user_id/{id}")
	private List<CardDTO> getUserCards(@PathVariable String id) {
		return cardService.getUserCards(Integer.valueOf(id));
	}

	@RequestMapping(method=RequestMethod.GET, value="/cards/rand")
	private List<CardDTO> getRandCards(@RequestParam Integer userId, @RequestParam Integer cardNumber) {
		return cardService.getRandCardDTO(userId, cardNumber);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/card/{id}")
	private CardDTO getCard(@PathVariable String id) {
		Optional<CardJPA> card;
		card= cardService.getCard(Integer.valueOf(id));
		if(card.isPresent()) {
			return cardMapper.toCardDTO(card.get());
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card id:"+id+", not found",null);

	}
	
	@RequestMapping(method=RequestMethod.POST,value="/card")
	public CardDTO addCard(@RequestBody CardDTO card) {
		return cardService.addCard(cardMapper.toCardJPA(card), ASYNC);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/card/{id}")
	public CardDTO updateCard(@RequestBody CardDTO card, @PathVariable String id, @RequestParam(required=false) Integer transactionId) {
		card.setId(Integer.valueOf(id));
		return cardService.updateCard(cardMapper.toCardJPA(card), ASYNC, transactionId);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/card/{id}")
	public void deleteUser(@PathVariable String id) {
		cardService.deleteCardAsync(Integer.valueOf(id));
	}

	@RequestMapping(method=RequestMethod.GET, value="/cards_to_sell")
	private List<CardDTO> getCardsToSell() {
		List<CardDTO> list=new ArrayList<>();
		for(CardJPA c : cardService.getAllCardToSell()){
			CardDTO cLight = cardMapper.toCardDTO(c);
			list.add(cLight);
		}
		return list;

	}

}
