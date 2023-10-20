package com.fxmxracingteam.userlib.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private Integer id;
	private String surName;
	private String lastName;
	private String login;
	private String pwd;
	private String email;
	private Float account;
	private Set<Integer> cardList;
	
}