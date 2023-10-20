package com.fxmxracingteam.cardlib.extension;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardBasics {
	
	public CardBasics(CardBasics c) {
		this(c.getName(), c.getDescription(), c.getFamily(), c.getAffinity(), c.getImgUrl(), c.getSmallImgUrl());
	}
	
	private String name;
	private String description;
	private String family;
	private String affinity;
	private String imgUrl;
	private String smallImgUrl;

}
