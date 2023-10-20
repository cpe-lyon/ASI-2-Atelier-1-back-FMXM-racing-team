package com.fxmxracingteam.cardservice.jpa;

import com.fxmxracingteam.cardlib.extension.CardBasics;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CardJPA extends CardBasics {
	
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

	public CardJPA(String name, String description, String family, String affinity, float energy, float hp,
					 float defence, float attack,String imgUrl,String smallImg,float price) {
		super(name, description, family, affinity,imgUrl,smallImg);
		this.energy = energy;
		this.hp = hp;
		this.defence = defence;
		this.attack = attack;
		//this.price=price;
		this.price=this.computePrice();
	}

	
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
	
	public float computePrice() {
		return this.hp * 20 + this.defence*20 + this.energy*20 + this.attack*20;
	}

	
}
