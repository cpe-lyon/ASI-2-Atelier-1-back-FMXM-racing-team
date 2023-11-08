package com.fxmxracingteam.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fxmxracingteam.cardlib.api.CardApiRestService;
import com.fxmxracingteam.cardlib.dto.CardDTO;
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
	private CardApiRestService cardApiRestService;

	public UserService(UserRepository userRepository, UserMapper userMapper, UserAsyncService userAsyncService) {
		this.cardApiRestService = new CardApiRestService();
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
		if (async) {
			userAsyncService.createUserDTO(user);
		}
		UserJPA u = userMapper.toUserJPA(user);
		userRepository.save(u);
		List<CardDTO> cardList = cardApiRestService.getRandCard(5);
		for (CardDTO card : cardList) {
			u.addCard(card.getId());
		}
		UserJPA uBd = userRepository.save(u);
		return userMapper.toUserDTO(uBd);
	}

	public UserDTO updateUser(UserDTO user, Boolean async) {
		if (async) {
			userAsyncService.updateUserDTO(user);
		}
		UserJPA u = userMapper.toUserJPA(user);
		return updateUser(u);
	}

	public UserDTO updateUser(UserJPA user) {
		UserJPA uBd = userRepository.save(user);
		return userMapper.toUserDTO(uBd);
	}

	public void deleteUser(String id, Boolean async) {
		if (async) {
			userAsyncService.deleteUser(id);
		}
		userRepository.deleteById(Integer.valueOf(id));
	}

	public Integer getUserByLoginPwd(String login, String pwd) {
		List<UserJPA> ulist = null;
		ulist = userRepository.findByLoginAndPwd(login, pwd);
		Integer uId = ulist.get(0).getId();
		return uId;
	}

	private UserJPA fromUDtoToUModel(UserDTO user) {
		UserJPA u = userMapper.toUserJPA(user);
		//List<CardModel> cardList = new ArrayList<CardModel>();
		//for (Integer cardId : user.getCardList()) {
		//	Optional<CardModel> card = cardService.getCard(cardId);
		//	if (card.isPresent()) {
		//		cardList.add(card.get());
		//	}
		//}
		return u;
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
