package com.fxmxracingteam.storeservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fxmxracingteam.cardlib.api.CardApiRestService;
import com.fxmxracingteam.cardlib.dto.CardDTO;
import com.fxmxracingteam.storelib.enums.StoreAction;
import com.fxmxracingteam.storeservice.jpa.StoreTransactionJPA;
import com.fxmxracingteam.storeservice.repository.StoreTransactionRepository;
import com.fxmxracingteam.userlib.api.UserApiRestService;
import com.fxmxracingteam.userlib.dto.UserDTO;

@Service
public class StoreService {
	
	@Autowired
	private StoreTransactionRepository storeTransactionRepository;
	
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
			user.addCard(card_id);
			user.setAccount(user.getAccount() - card.getPrice());
			userApiRestService.updateUser(user, user_id.toString());
			StoreTransactionJPA sT = new StoreTransactionJPA(user_id, card_id, StoreAction.BUY);
			storeTransactionRepository.save(sT);
			return true;
		} else {
			return false;
		}
	}

	public boolean sellCard(Integer user_id, Integer card_id) {
		//Optional<UserModel> u_option = userService.getUser(user_id);
		//Optional<CardModel> c_option = cardService.getCard(card_id);
		//if (!u_option.isPresent() || !c_option.isPresent()) {
		//	return false;
		//}
		//UserModel u = u_option.get();
		//CardModel c = c_option.get();
		//
		//c.setUser(null);
		//cardService.updateCard(c);
		//u.setAccount(u.getAccount() + c.computePrice());
		//userService.updateUser(u);
		StoreTransactionJPA sT = new StoreTransactionJPA(user_id, card_id, StoreAction.SELL);
		storeTransactionRepository.save(sT);
		return true;
	}

	public List<StoreTransactionJPA> getAllTransactions() {
		List<StoreTransactionJPA> sTList = new ArrayList<>();
		this.storeTransactionRepository.findAll().forEach(sTList::add);
		return sTList;

	}


}
