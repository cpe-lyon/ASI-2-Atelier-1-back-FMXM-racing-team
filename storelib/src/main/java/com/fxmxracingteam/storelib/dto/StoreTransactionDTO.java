package com.fxmxracingteam.storelib.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fxmxracingteam.storelib.enums.StoreAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreTransactionDTO implements Serializable {

	private static final long serialVersionUID = -7393242192806989476L;
	private Integer id;
	private Integer userId;
	private Integer cardId;
	private StoreAction action;
	private Timestamp timeSt;
	private boolean userState;
	private boolean cardState;
	private TransactionState state;
}
