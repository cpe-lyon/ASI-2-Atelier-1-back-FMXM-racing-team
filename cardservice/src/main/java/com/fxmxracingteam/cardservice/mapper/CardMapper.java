package com.fxmxracingteam.cardservice.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.fxmxracingteam.cardlib.dto.CardDTO;
import com.fxmxracingteam.cardservice.jpa.CardJPA;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {


	CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

	// Vous n'avez pas besoin de déclarer des mappings pour les champs qui ont le même nom et type
	CardDTO toCardDTO(CardJPA cardJPA);

	// MapStruct devrait être capable de comprendre l'inversion du mapping sans déclarer de nouveau tous les champs
	@InheritInverseConfiguration(name = "toCardDTO")
	CardJPA toCardJPA(CardDTO cardDTO);

}
