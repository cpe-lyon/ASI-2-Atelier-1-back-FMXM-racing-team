package com.fxmxracingteam.cardlib.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO implements Serializable {

	private static final long serialVersionUID = 8807901736682572113L;
	private Integer id;
	private Float hp;
	private Float energy;
	private Float attack;
	private Float defence;
	private Float price;
	private Integer userId;
	
	private String name;
	private String description;
	private String family;
	private String affinity;
	private String imgUrl;
	private String smallImgUrl;
	
}
