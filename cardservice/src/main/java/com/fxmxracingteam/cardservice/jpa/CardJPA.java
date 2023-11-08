package com.fxmxracingteam.cardservice.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CardJPA {
	
	public CardJPA(CardReferenceJPA cardReferenceJPA) {
		this.name = cardReferenceJPA.getName();
		this.description = cardReferenceJPA.getDescription();
		this.family = cardReferenceJPA.getFamily();
		this.affinity = cardReferenceJPA.getAffinity();
		this.imgUrl = cardReferenceJPA.getImgUrl();
		this.smallImgUrl = cardReferenceJPA.getSmallImgUrl();
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
	
	private String name;
	private String description;
	private String family;
	private String affinity;
	private String imgUrl;
	private String smallImgUrl;
	
	public float computePrice() {
		return this.hp * 20 + this.defence*20 + this.energy*20 + this.attack*20;
	}

	
}
