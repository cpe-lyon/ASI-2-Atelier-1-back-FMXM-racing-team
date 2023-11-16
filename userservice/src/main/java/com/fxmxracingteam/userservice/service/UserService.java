package com.fxmxracingteam.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fxmxracingteam.cardlib.api.CardApiRestService;
import com.fxmxracingteam.cardlib.dto.CardDTO;
import com.fxmxracingteam.storelib.api.StoreApiRestService;
import com.fxmxracingteam.storelib.dto.OperationType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fxmxracingteam.userlib.dto.UserDTO;
import com.fxmxracingteam.userservice.jpa.UserJPA;
import com.fxmxracingteam.userservice.mapper.UserMapper;
import com.fxmxracingteam.userservice.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final UserAsyncService userAsyncService;
	private final CardApiRestService cardApiRestService;
	private final StoreApiRestService storeApiRestService;

	public UserService(UserRepository userRepository, UserMapper userMapper, UserAsyncService userAsyncService) {
		this.cardApiRestService = new CardApiRestService();
		this.storeApiRestService = new StoreApiRestService();
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.userAsyncService = userAsyncService;
	}

	public List<UserJPA> getAllUsers() {
        return new ArrayList<>(userRepository.findAll());
	}

	public List<UserDTO> getAllUsersDTO() {
		List<UserDTO> uDTOList=new ArrayList<UserDTO>();
		for(UserJPA uM: getAllUsers()){
			uDTOList.add(userMapper.toUserDTO(uM));
		}
		return uDTOList;
	}

	public Optional<UserJPA> getUser(String id) {
		return userRepository.findById(Integer.valueOf(id));
	}

	public Optional<UserJPA> getUser(Integer id) {
		return userRepository.findById(id);
	}

	public UserDTO addUser(UserDTO user, Boolean async) {
		user.setAccount(0f);
		if (async) {
			userAsyncService.createUserDTO(user);
			return null;
		}
		UserJPA u = userMapper.toUserJPA(user);
		u = userRepository.save(u);
		List<CardDTO> cardList = cardApiRestService.getRandCard(u.getId(), 5);
		for (CardDTO card : cardList) {
			u.addCard(card.getId());
		}
		UserJPA uBd = userRepository.save(u);
		return userMapper.toUserDTO(uBd);
	}

	public UserDTO updateUser(UserDTO user, Boolean async, Integer transactionId) {
		if (async) {
			userAsyncService.updateUserDTO(user, transactionId);
			return null;
		}
		UserJPA u = userMapper.toUserJPA(user);
		return updateUser(u, transactionId);
	}

	public UserDTO updateUser(UserJPA user, Integer transactionId) {
		UserJPA uBd = userRepository.save(user);
		storeApiRestService.updateTransactionOperation(transactionId, OperationType.USER.toString(), true);
		return userMapper.toUserDTO(uBd);
	}

	public void deleteUser(String id, Boolean async) {
		if (async) {
			userAsyncService.deleteUser(id);
		} else {
			userRepository.deleteById(Integer.valueOf(id));
		}
	}

	public Integer getUserByLoginPwd(String login, String pwd) {
		List<UserJPA> ulist = null;
		ulist = userRepository.findByLoginAndPwd(login, pwd);
		if(ulist.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User login:"+login+", not found",null);
		}
		return ulist.get(0).getId();
	}

	public UserDTO getUserDTO(String id) {
		Optional<UserJPA> ruser;
		ruser=getUser(id);
		if(ruser.isPresent()) {
			return userMapper.toUserDTO(ruser.get());
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id:"+id+", not found",null);
	}
}
