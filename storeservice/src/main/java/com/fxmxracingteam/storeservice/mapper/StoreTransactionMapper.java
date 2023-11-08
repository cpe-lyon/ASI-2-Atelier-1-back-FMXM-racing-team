package com.fxmxracingteam.storeservice.mapper;

import org.mapstruct.Mapper;

import com.fxmxracingteam.storelib.dto.StoreTransactionDTO;
import com.fxmxracingteam.storeservice.jpa.StoreTransactionJPA;

@Mapper
public interface StoreTransactionMapper {
	
	StoreTransactionDTO toStoreTransactionDTO(StoreTransactionJPA storeTransactionJPA);
	
	StoreTransactionJPA toStoreTransactionJPA(StoreTransactionDTO storeTransactionDTO);

}
