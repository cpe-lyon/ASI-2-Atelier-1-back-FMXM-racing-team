package com.fxmxracingteam.userservice.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class UserJPA implements Serializable {
	
	public UserJPA() {
		this.login = "";
		this.pwd = "";
		this.lastName="lastname_default";
		this.surName="surname_default";
		this.email="email_default";
	}

	public UserJPA(String login, String pwd) {
		super();
		this.login = login;
		this.pwd = pwd;
		this.lastName="lastname_default";
		this.surName="surname_default";
		this.email="email_default";
	}

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String login;
	private String pwd;
	private Float account;
	private String lastName;
	private String surName;
	private String email;
	private Set<Integer> cardIdList = new HashSet<>();
	
	public void addAllCardList(Collection<Integer> cardIdList) {
		this.cardIdList.addAll(cardIdList);
	}
	
	public void addCard(Integer cardId) {
		this.cardIdList.add(cardId);
	}

	@SuppressWarnings("unused")
	private boolean checkIfCard(Integer cardId){
		for(Integer c_cardId: this.cardIdList){
			if(c_cardId == cardId){
				return true;
			}
		}
		return false;
	}



}
