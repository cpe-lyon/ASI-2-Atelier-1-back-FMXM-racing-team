package com.fxmxracingteam.userservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fxmxracingteam.userlib.dto.AuthDTO;
import com.fxmxracingteam.userlib.dto.UserDTO;
import com.fxmxracingteam.userservice.jpa.UserJPA;
import com.fxmxracingteam.userservice.mapper.UserMapper;
import com.fxmxracingteam.userservice.service.UserService;

@CrossOrigin
@RestController
public class UserRestController {
	private final UserService userService;

	@Value("${userservice.isAsync:true}")
	private Boolean isAsync;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method=RequestMethod.GET,value="/users")
	private List<UserDTO> getAllUsers() {
		return userService.getAllUsersDTO();

	}
	
	@RequestMapping(method=RequestMethod.GET,value="/user/{id}")
	private UserDTO getUser(@PathVariable String id) {
		return userService.getUserDTO(id);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user")
	public UserDTO addUser(@RequestBody UserDTO user) {
		return userService.addUser(user, isAsync);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/user/{id}")
	public UserDTO updateUser(@RequestBody UserDTO user,@PathVariable String id) {
		user.setId(Integer.valueOf(id));
		return userService.updateUser(user, isAsync);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/user/{id}")
	public void deleteUser(@PathVariable String id) {
		userService.deleteUser(id, isAsync);
	}


}
