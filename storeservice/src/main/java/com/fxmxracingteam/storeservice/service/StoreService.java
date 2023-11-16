package com.fxmxracingteam.storeservice.service;

import java.util.ArrayList;
import java.util.List;

import com.fxmxracingteam.storelib.dto.OperationType;
import com.fxmxracingteam.storelib.dto.TransactionState;
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
	
	private final StoreTransactionRepository storeTransactionRepository;
	
	private final StoreAsyncService storeAsyncService;
	
	private final StoreTransactionMapper storeTransactionMapper;
	
	private UserApiRestService userApiRestService;
	private CardApiRestService cardApiRestService;
	
	public StoreService(StoreTransactionRepository storeTransactionRepository, StoreAsyncService storeAsyncService, StoreTransactionMapper storeTransactionMapper) {
		userApiRestService = new UserApiRestService();
		cardApiRestService = new CardApiRestService();
		this.storeTransactionRepository = storeTransactionRepository;
		this.storeAsyncService = storeAsyncService;
		this.storeTransactionMapper = storeTransactionMapper;
	}

	public boolean buyCard(Integer user_id, Integer card_id) {
		UserDTO user = userApiRestService.getUser(user_id.toString());
		CardDTO card = cardApiRestService.getCard(card_id.toString());
		if (user == null || card == null) {
			return false; 
		}
		if (user.getAccount() >= card.getPrice()) {
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

	private void checkIfTransactionPassed(StoreTransactionJPA storeTransactionJPA) {
		if(storeTransactionJPA.isCardOperation() && storeTransactionJPA.isUserOperation()) {
			storeTransactionJPA.setState(TransactionState.SUCCESS);
			storeTransactionRepository.save(storeTransactionJPA);
		}
	}

	public void updateTransactionOperation(Integer storeId, OperationType operationType, Boolean isSuccessful) {
		StoreTransactionJPA storeTransactionJPA = storeTransactionRepository.findById(storeId).orElse(null);
		if (storeTransactionJPA != null) {
			switch (operationType) {
				case USER:
					storeTransactionJPA.setUserOperation(isSuccessful);
					break;
				case CARD:
					storeTransactionJPA.setCardOperation(isSuccessful);
					break;
			}
			storeTransactionRepository.save(storeTransactionJPA);
			checkIfTransactionPassed(storeTransactionJPA);
		}
	}
}
