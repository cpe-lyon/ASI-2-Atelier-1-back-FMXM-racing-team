package com.fxmxracingteam.cardservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.fxmxracingteam.storelib.api.StoreApiRestService;
import com.fxmxracingteam.storelib.dto.OperationType;
import org.springframework.stereotype.Service;

import com.fxmxracingteam.cardlib.dto.CardDTO;
import com.fxmxracingteam.cardservice.jpa.CardJPA;
import com.fxmxracingteam.cardservice.jpa.CardReferenceJPA;
import com.fxmxracingteam.cardservice.mapper.CardMapper;
import com.fxmxracingteam.cardservice.repository.CardRepository;

@Service
public class CardService {
	
	private final CardRepository cardRepository;
	
	private final CardReferenceService cardRefService;
	
	private final CardAsyncService cardAsyncService;
	private final StoreApiRestService storeApiRestService;

	private final CardMapper cardMapper;
	
	private Random rand = new Random();

	public CardService(CardRepository cardRepository, CardReferenceService cardRefService, CardAsyncService cardAsyncService, CardMapper cardMapper) {
		this.cardRepository = cardRepository;
		this.cardRefService = cardRefService;
		this.cardAsyncService = cardAsyncService;
		this.cardMapper = cardMapper;
		this.storeApiRestService = new StoreApiRestService();
	}

	public List<CardJPA> getAllCardModel() {
		List<CardJPA> cardList = new ArrayList<>();
		cardRepository.findAll().forEach(cardList::add);
		return cardList;
	}

	public CardDTO addCard(CardJPA cardModel, Boolean async) {
		if (async) {
			CardDTO cardDTO = cardMapper.toCardDTO(cardModel);
			cardAsyncService.sendAddCard(cardDTO);
			return cardDTO;
		}
		CardJPA cDb=cardRepository.save(cardModel);
		return cardMapper.toCardDTO(cDb);
	}

	public void updateCardRef(CardJPA cardModel) {
		cardRepository.save(cardModel);

	}
	
	public CardDTO updateCard(CardJPA cardModel, Boolean async, Integer transactionId) {
		if (async) {
			CardDTO cardDTO = cardMapper.toCardDTO(cardModel);
			cardAsyncService.sendUpdateCard(cardDTO, transactionId);
		}
		CardJPA cDb=cardRepository.save(cardModel);
		if(transactionId!=null) {
			storeApiRestService.updateTransactionOperation(transactionId, OperationType.CARD.toString(), true);
		}
		return cardMapper.toCardDTO(cDb);
	}
	
	public Optional<CardJPA> getCard(Integer id) {
		return cardRepository.findById(id);
	}
	
	public void deleteCardAsync(Integer id) {
		CardJPA cardModelToDelete = cardRepository.findById(id).orElse(null);
		if (cardModelToDelete != null) {
			CardDTO cardDTO = cardMapper.toCardDTO(cardModelToDelete);
			cardAsyncService.sendDeleteCard(cardDTO);
		}
	}

	public void deleteCardAsync(CardJPA cardJPA) {
		cardRepository.delete(cardJPA);
	}
	
	public List<CardJPA> getRandCard(Integer userId, Integer nbr){
		List<CardJPA> cardList=new ArrayList<>();
		for(int i=0;i<nbr;i++) {
			CardReferenceJPA currentCardRef = cardRefService.getRandCardRef();
			CardJPA currentCard= new CardJPA(currentCardRef);
			currentCard.setId(null);
			currentCard.setAttack(rand.nextFloat()*100);
			currentCard.setDefence(rand.nextFloat()*100);
			currentCard.setEnergy(100f);
			currentCard.setHp(rand.nextFloat()*100);
			currentCard.setPrice(currentCard.computePrice());
			currentCard.setUserId(userId);
			CardJPA cardSaved = cardRepository.save(currentCard);
			cardList.add(cardSaved);	
		}
		return cardList;
	}

	public List<CardDTO> getRandCardDTO(Integer userId, Integer nbr) {
		List<CardDTO> cLightList=new ArrayList<>();
		for(CardJPA c:getRandCard(userId, nbr)) {
			CardDTO cLight = cardMapper.toCardDTO(c);
			cLightList.add(cLight);
		}
		return cLightList;
	}


	public List<CardJPA> getAllCardToSell(){
		//No userId on a card means it is to sell
		return this.cardRepository.findByUserId(null);
	}
	
	public List<CardDTO> getUserCards(Integer userId) {
		return this.cardMapper.toCardDTO(this.cardRepository.findByUserId(userId));
	}


}
