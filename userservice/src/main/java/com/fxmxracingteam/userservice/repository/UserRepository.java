package com.fxmxracingteam.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fxmxracingteam.userservice.jpa.UserJPA;

@Repository
public interface UserRepository extends JpaRepository<UserJPA, Integer> {
	
	public List<UserJPA> findByLoginAndPwd(String login, String Pwd);

}
