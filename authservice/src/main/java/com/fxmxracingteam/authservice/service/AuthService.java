package com.fxmxracingteam.authservice.service;

import org.springframework.stereotype.Service;

import com.fxmxracingteam.userlib.api.UserApiRestService;
import com.fxmxracingteam.userlib.dto.AuthDTO;

@Service
public class AuthService {
	
	private UserApiRestService userApiRestService;
	
	public AuthService() {
		userApiRestService = new UserApiRestService();
	}

	public Integer login(AuthDTO authDto) {
		return userApiRestService.authenticateUser(authDto);
	}
	
}
