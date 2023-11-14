package com.fxmxracingteam.authservice.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.fxmxracingteam.userlib.api.UserApiRestService;
import com.fxmxracingteam.userlib.dto.AuthDTO;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
	
	private UserApiRestService userApiRestService;
	
	public AuthService() {
		userApiRestService = new UserApiRestService();
	}

	public Integer login(AuthDTO authDto) {
		try {
			return userApiRestService.authenticateUser(authDto);
		} catch (Exception e) {
			return -1;
		}
	}
	
}
