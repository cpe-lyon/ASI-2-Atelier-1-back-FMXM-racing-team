package com.fxmxracingteam.cardservice.mapper;

import org.mapstruct.Mapper;

import com.fxmxracingteam.cardlib.dto.CardDTO;
import com.fxmxracingteam.cardservice.jpa.CardJPA;

@Mapper
public interface CardMapper {
	
	public CardDTO toCardDTO(CardJPA cardJPA);
	
	public CardJPA toCardJPA(CardDTO cardDTO);

}
