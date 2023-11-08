package com.fxmxracingteam.userservice.mapper;

import org.mapstruct.Mapper;

import com.fxmxracingteam.userlib.dto.UserDTO;
import com.fxmxracingteam.userservice.jpa.UserJPA;

@Mapper
public interface UserMapper {
	
	UserJPA toUserJPA(UserDTO userDTO);
	
	UserDTO toUserDTO(UserJPA userJPA);

}
