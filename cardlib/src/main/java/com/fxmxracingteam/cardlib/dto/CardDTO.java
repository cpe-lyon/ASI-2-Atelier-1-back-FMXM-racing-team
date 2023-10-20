package com.fxmxracingteam.cardlib.dto;

import com.fxmxracingteam.cardlib.extension.CardBasics;
import com.fxmxracingteam.cardservice.jpa.CardJPA;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO extends CardBasics {

	private Integer id;
	private Float hp;
	private Float energy;
	private Float attack;
	private Float defence;
	private Float price;
	private Integer userId;
	
}
