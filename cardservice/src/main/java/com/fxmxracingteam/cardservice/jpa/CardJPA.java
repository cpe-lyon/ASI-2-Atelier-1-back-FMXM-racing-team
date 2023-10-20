package com.fxmxracingteam.cardservice.jpa;

import jakarta.persistence.Entity;
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
public class CardJPA {
	
	@Id
	private Integer id;
	private String name;	
	private String family;
	private String affinity;
	private String description;
	private String imgUrl;
	private String smallImgUrl;
	private Float hp;
	private Float energy;
	private Float attack;
	private Float defence;
	private Float price;
	private Integer userId;
	private Integer storeId;
	
}
