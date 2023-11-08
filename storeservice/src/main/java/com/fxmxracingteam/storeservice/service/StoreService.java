package com.fxmxracingteam.storeservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fxmxracingteam.cardlib.api.CardApiRestService;
import com.fxmxracingteam.cardlib.dto.CardDTO;
import com.fxmxracingteam.storelib.enums.StoreAction;
import com.fxmxracingteam.storeservice.jpa.StoreTransactionJPA;
import com.fxmxracingteam.storeservice.mapper.StoreTransactionMapper;
import com.fxmxracingteam.storeservice.repository.StoreTransactionRepository;
import com.fxmxracingteam.userlib.api.UserApiRestService;
import com.fxmxracingteam.userlib.dto.UserDTO;

@Service
public class StoreService {
	
	@Autowired
	private StoreTransactionRepository storeTransactionRepository;
	
	@Autowired
	private StoreAsyncService storeAsyncService;
	
	@Autowired
	private StoreTransactionMapper storeTransactionMapper;
	
	private UserApiRestService userApiRestService;
	private CardApiRestService cardApiRestService;
	
	public StoreService() {
		userApiRestService = new UserApiRestService();
		cardApiRestService = new CardApiRestService();
	}

	public boolean buyCard(Integer user_id, Integer card_id) {
		UserDTO user = userApiRestService.getUser(user_id.toString());
		CardDTO card = cardApiRestService.getCard(card_id.toString());
		if (user == null || card == null) {
			return false; 
		}
		if (user.getAccount() > card.getPrice()) {
			card.setUserId(user_id);
			cardApiRestService.updateCard(card, card_id.toString());
			user.addCard(card_id);
			user.setAccount(user.getAccount() - card.getPrice());
			userApiRestService.updateUser(user, user_id.toString());
			StoreTransactionJPA sT = new StoreTransactionJPA(user_id, card_id, StoreAction.BUY);
			storeAsyncService.sendStoreTransaction(storeTransactionMapper.toStoreTransactionDTO(sT));
			return true;
		} else {
			return false;
		}
	}

	public boolean sellCard(Integer user_id, Integer card_id) {
		UserDTO user = userApiRestService.getUser(user_id.toString());
		CardDTO card = cardApiRestService.getCard(card_id.toString());
		if (user == null || card == null) {
			return false; 
		}
		card.setUserId(null);
		cardApiRestService.updateCard(card, card_id.toString());
		user.getCardIdList().remove(card_id);
		user.setAccount(user.getAccount() + card.getPrice());
		userApiRestService.updateUser(user, user_id.toString());
		StoreTransactionJPA sT = new StoreTransactionJPA(user_id, card_id, StoreAction.SELL);
		storeAsyncService.sendStoreTransaction(storeTransactionMapper.toStoreTransactionDTO(sT));
		return true;
	}
	
	public void saveTransaction(StoreTransactionJPA storeTransactionModel) {
		storeTransactionRepository.save(storeTransactionModel);
	}

	public List<StoreTransactionJPA> getAllTransactions() {
		List<StoreTransactionJPA> sTList = new ArrayList<>();
		this.storeTransactionRepository.findAll().forEach(sTList::add);
		return sTList;

	}


}
