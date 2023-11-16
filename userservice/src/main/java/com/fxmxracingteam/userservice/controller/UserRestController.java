package com.fxmxracingteam.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.fxmxracingteam.userlib.dto.AuthDTO;
import com.fxmxracingteam.userlib.dto.UserDTO;
import com.fxmxracingteam.userservice.service.UserService;

@CrossOrigin
@RestController
public class UserRestController {
	private final UserService userService;

	private static Boolean isAsync = true;

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
	public UserDTO updateUser(@RequestBody UserDTO user, @PathVariable String id, @RequestParam(required=false) Integer transactionId) {
		user.setId(Integer.valueOf(id));
		return userService.updateUser(user, isAsync, transactionId);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/user/{id}")
	public void deleteUser(@PathVariable String id) {
		userService.deleteUser(id, isAsync);
	}

	@RequestMapping(method=RequestMethod.POST,value="/user/login")
	public Integer findUserByLoginAndPwd(@RequestBody AuthDTO authDTO) {
		return userService.getUserByLoginPwd(authDTO.getUsername(), authDTO.getPassword());
	}

}
