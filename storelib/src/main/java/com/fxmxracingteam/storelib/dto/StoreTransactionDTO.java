package com.fxmxracingteam.storelib.dto;

import java.security.Timestamp;

import com.fxmxracingteam.storelib.enums.StoreAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreTransactionDTO {

	private Integer id;
	private Integer userId;
	private Integer cardId;
	private StoreAction action;
	private Timestamp timeSt;
	
}
