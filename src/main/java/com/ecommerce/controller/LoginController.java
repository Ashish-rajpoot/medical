package com.ecommerce.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.global.GlobalData;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.services.UserService;

@Controller

public class LoginController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	
	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/register")
	public String postRegister(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//		user.setRole("ROLE_USER");
//		user.setRole("ROLE_ADMIN");
			userRepository.save(user);
			System.out.println("User Register SuccecssFully");
			return "redirect:/login";
	}
	
	@GetMapping("/index")
	public String home(Model model) {
		return "index";
	}

		@GetMapping("/default")
		public String defaultAfterLogin(HttpServletRequest request) {
			if(request.isUserInRole("ROLE_ADMIN")) {
				return "redirect:/admin/";
			}
			return "redirect:/user/";
		}
}
