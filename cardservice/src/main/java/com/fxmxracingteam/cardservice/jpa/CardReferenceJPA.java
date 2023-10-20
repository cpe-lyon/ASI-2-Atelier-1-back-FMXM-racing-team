package com.fxmxracingteam.cardservice.jpa;

import java.io.Serializable;

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
public class CardReferenceJPA extends CardBasics implements Serializable {
	
	public CardReferenceJPA(String name, String description, String family, String affinity,String imgUrl,String smallImgUrl) {
		super(name, description, family,affinity,imgUrl,smallImgUrl);
	}


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	
}
