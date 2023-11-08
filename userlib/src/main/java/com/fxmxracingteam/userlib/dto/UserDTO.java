package com.fxmxracingteam.userlib.dto;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
	
	private Integer id;
	private String surName;
	private String lastName;
	private String login;
	private String pwd;
	private String email;
	private Float account;
	private Set<Integer> cardIdList;
	
	public void addCard(Integer cardId) {
		this.cardIdList.add(cardId);
	}
	
}
