package com.fxmxracingteam.cardservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.fxmxracingteam.cardservice.jpa.CardReferenceJPA;
import com.fxmxracingteam.cardservice.repository.CardReferenceRepository;

@Service
public class CardReferenceService {
	
	@Autowired
	private CardReferenceRepository cardReferenceRepository;

	public List<CardReferenceJPA> getAllCardRef() {
		List<CardReferenceJPA> cardRefList = new ArrayList<>();
		cardReferenceRepository.findAll().forEach(cardRefList::add);
		return cardRefList;
	}

	public void addCardRef(CardReferenceJPA cardRef) {
		cardReferenceRepository.save(cardRef);
	}

	public void updateCardRef(CardReferenceJPA cardRef) {
		cardReferenceRepository.save(cardRef);

	}

	public CardReferenceJPA getRandCardRef() {
		List<CardReferenceJPA> cardRefList=getAllCardRef();
		if (!cardRefList.isEmpty()) {
			Random rand=new Random();
			int rindex=rand.nextInt(cardRefList.size()-1);
			return cardRefList.get(rindex);
		}
		return null;
	}

	/**
	 * Executed after application start
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void doInitAfterStartup() {
		for(int i=0;i<10;i++){
			CardReferenceJPA cardRef =new CardReferenceJPA(null, "name"+i,"description"+i,"family"+i,"affinity"+i,"http://medias.3dvf.com/news/sitegrab/gits2045.jpg","https://cdn.animenewsnetwork.com/thumbnails/fit600x1000/cms/feature/89858/05.jpg");
			addCardRef(cardRef);
			i++;
		}
	}


}
