package com.fxmxracingteam.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fxmxracingteam.authservice.service.AuthService;
import com.fxmxracingteam.userlib.dto.AuthDTO;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthRestController {
	
	@Autowired
	AuthService authService;
	
	
	@RequestMapping(method=RequestMethod.POST)
	private Integer login(@RequestBody AuthDTO authDto) {
		
		return authService.login(authDto);

	}

}
