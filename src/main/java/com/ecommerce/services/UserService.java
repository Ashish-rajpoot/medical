package com.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;

@Service
public class UserService  {

	
	@Autowired
	UserRepository userRepository;
	
	
	
//	public boolean getExitingUser(String email) {
//			return userRepository.exitEmailId(email);
//	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	
	
}
