package com.fxmxracingteam.storeservice.jpa;

import java.sql.Timestamp;

import com.fxmxracingteam.storelib.enums.StoreAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class StoreTransactionJPA {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer userId;
	private Integer cardId;
	private StoreAction action;
    private Timestamp timeSt;
    
    public StoreTransactionJPA() {
		this.timeSt=new Timestamp(System.currentTimeMillis());
	}

	public StoreTransactionJPA( Integer userId, Integer cardId, StoreAction action) {
		super();
		this.userId = userId;
		this.cardId = cardId;
		this.action = action;
		this.timeSt=new Timestamp(System.currentTimeMillis());
	}
	
}
