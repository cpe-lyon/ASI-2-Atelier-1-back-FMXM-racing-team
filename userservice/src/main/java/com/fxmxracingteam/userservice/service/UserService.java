package com.fxmxracingteam.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fxmxracingteam.userlib.dto.UserDTO;
import com.fxmxracingteam.userservice.jpa.UserJPA;
import com.fxmxracingteam.userservice.mapper.UserMapper;
import com.fxmxracingteam.userservice.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	//private final CardModelService cardModelService;
	//Replace par lib commune API

	public List<UserJPA> getAllUsers() {
		List<UserJPA> userList = new ArrayList<>();
		userRepository.findAll().forEach(userList::add);
		return userList;
	}

	public Optional<UserJPA> getUser(String id) {
		return userRepository.findById(Integer.valueOf(id));
	}

	public Optional<UserJPA> getUser(Integer id) {
		return userRepository.findById(id);
	}

	public UserDTO addUser(UserDTO user) {
		UserJPA u = userMapper.toUserJPA(user);
		// needed to avoid detached entity passed to persist error
		userRepository.save(u);
		//List<UserJPA> cardList = cardModelService.getRandCard(5);
		//for (UserJPA card : cardList) {
		//	u.addCard(card);
		//}
		UserJPA uBd = userRepository.save(u);
		return userMapper.toUserDTO(uBd);
	}

	public UserDTO updateUser(UserDTO user) {
		UserJPA u = userMapper.toUserJPA(user);
		UserJPA uBd =userRepository.save(u);
		return userMapper.toUserDTO(uBd);
	}

	public UserDTO updateUser(UserJPA user) {
		UserJPA uBd = userRepository.save(user);
		return userMapper.toUserDTO(uBd);
	}

	public void deleteUser(String id) {
		userRepository.deleteById(Integer.valueOf(id));
	}

	public List<UserJPA> getUserByLoginPwd(String login, String pwd) {
		List<UserJPA> ulist = null;
		ulist = userRepository.findByLoginAndPwd(login, pwd);
		return ulist;
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


}
