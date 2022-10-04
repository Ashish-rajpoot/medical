package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/")
	public String  index(Model model) {
		List<User> user =  userRepository.findAll();
		model.addAttribute("user",user);
		return "home";
	}
	
}
