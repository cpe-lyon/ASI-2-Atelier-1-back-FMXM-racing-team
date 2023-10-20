package com.fxmxracingteam.userservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping(method=RequestMethod.GET,value="/users")
	private List<UserDTO> getAllUsers() {
		List<UserDTO> uDTOList=new ArrayList<UserDTO>();
		for(UserJPA uM: userService.getAllUsers()){
			uDTOList.add(userMapper.toUserDTO(uM));
		}
		return uDTOList;

	}
	
	@RequestMapping(method=RequestMethod.GET,value="/user/{id}")
	private UserDTO getUser(@PathVariable String id) {
		Optional<UserJPA> ruser;
		ruser= userService.getUser(id);
		if(ruser.isPresent()) {
			return userMapper.toUserDTO(ruser.get());
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id:"+id+", not found",null);

	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user")
	public UserDTO addUser(@RequestBody UserDTO user) {
		return userService.addUser(user);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/user/{id}")
	public UserDTO updateUser(@RequestBody UserDTO user,@PathVariable String id) {
		user.setId(Integer.valueOf(id));
		return userService.updateUser(user);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/user/{id}")
	public void deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/auth")
	private Integer getAllCourses(@RequestBody AuthDTO authDto) {
		 List<UserJPA> uList = userService.getUserByLoginPwd(authDto.getUsername(),authDto.getPassword());
		if( uList.size() > 0) {
			
			return uList.get(0).getId();
		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Authentification Failed",null);

	}


}
