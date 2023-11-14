package com.fxmxracingteam.cardservice.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.fxmxracingteam.cardlib.dto.CardDTO;
import com.fxmxracingteam.cardservice.jpa.CardJPA;

@Mapper
public interface CardMapper {

	CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

	// Vous n'avez pas besoin de déclarer des mappings pour les champs qui ont le même nom et type
	CardDTO toCardDTO(CardJPA cardJPA);

	// MapStruct devrait être capable de comprendre l'inversion du mapping sans déclarer de nouveau tous les champs
	@InheritInverseConfiguration(name = "toCardDTO")
	CardJPA toCardJPA(CardDTO cardDTO);
	
	List<CardDTO> toCardDTO(List<CardJPA> cardJPA);
	
	List<CardJPA> toCardJPA(List<CardDTO> cardDTO);

}
