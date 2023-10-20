package com.fxmxracingteam.cardlib.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

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
	
}
