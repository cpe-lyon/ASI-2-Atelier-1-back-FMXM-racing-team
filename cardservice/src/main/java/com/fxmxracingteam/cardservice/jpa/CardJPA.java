package com.fxmxracingteam.cardservice.jpa;

import com.fxmxracingteam.cardlib.extension.CardBasics;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CardJPA extends CardBasics {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Float hp;
	private Float energy;
	private Float attack;
	private Float defence;
	private Float price;
	private Integer userId;
	private Integer storeId;

	public CardJPA( CardJPA cModel) {
		super(cModel);
		this.energy=cModel.getEnergy();
		this.hp=cModel.getHp();
		this.defence=cModel.getDefence();
		this.attack=cModel.getAttack();
		this.price=cModel.getPrice();
	}

	public CardJPA( CardBasics cardBasic) {
		super(cardBasic);
	}
	
	public float computePrice() {
		return this.hp * 20 + this.defence*20 + this.energy*20 + this.attack*20;
	}

	
}
