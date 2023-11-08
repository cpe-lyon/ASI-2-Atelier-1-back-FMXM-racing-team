package com.fxmxracingteam.storeservice.mapper;

import org.mapstruct.Mapper;

import com.fxmxracingteam.storelib.dto.StoreTransactionDTO;
import com.fxmxracingteam.storeservice.jpa.StoreTransactionJPA;
import org.mapstruct.Mapping;

@Mapper
public interface StoreTransactionMapper {

	@Mapping(target = "timeSt", source = "timeSt")
	StoreTransactionDTO toStoreTransactionDTO(StoreTransactionJPA storeTransactionJPA);
	
	StoreTransactionJPA toStoreTransactionJPA(StoreTransactionDTO storeTransactionDTO);

}
