package com.fxmxracingteam.cardservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fxmxracingteam.cardlib.dto.CardDTO;
import com.fxmxracingteam.cardservice.jpa.CardJPA;
import com.fxmxracingteam.cardservice.jpa.CardReferenceJPA;
import com.fxmxracingteam.cardservice.mapper.CardMapper;
import com.fxmxracingteam.cardservice.repository.CardRepository;

@Service
public class CardService {
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private CardReferenceService cardRefService;
	
	@Autowired
	private CardMapper cardMapper;
	
	private Random rand;
	
	public List<CardJPA> getAllCardModel() {
		List<CardJPA> cardList = new ArrayList<>();
		cardRepository.findAll().forEach(cardList::add);
		return cardList;
	}

	public CardDTO addCard(CardJPA cardModel) {
		CardJPA cDb=cardRepository.save(cardModel);
		return cardMapper.toCardDTO(cDb);
	}

	public void updateCardRef(CardJPA cardModel) {
		cardRepository.save(cardModel);

	}
	public CardDTO updateCard(CardJPA cardModel) {
		CardJPA cDb=cardRepository.save(cardModel);
		return cardMapper.toCardDTO(cDb);
	}
	public Optional<CardJPA> getCard(Integer id) {
		return cardRepository.findById(id);
	}
	
	public void deleteCardModel(Integer id) {
		cardRepository.deleteById(id);
	}
	
	public List<CardJPA> getRandCard(int nbr){
		List<CardJPA> cardList=new ArrayList<>();
		for(int i=0;i<nbr;i++) {
			CardReferenceJPA currentCardRef=cardRefService.getRandCardRef();
			CardJPA currentCard=new CardJPA(currentCardRef);
			currentCard.setAttack(rand.nextFloat()*100);
			currentCard.setDefence(rand.nextFloat()*100);
			currentCard.setEnergy(100f);
			currentCard.setHp(rand.nextFloat()*100);
			currentCard.setPrice(currentCard.computePrice());
			//save new card before sending for user creation
			//this.addCard(currentCard);
			cardList.add(currentCard);
		}
		return cardList;
	}


	public List<CardJPA> getAllCardToSell(){
		return this.cardRepository.findByUserId(null);
	}


}
