package com.ecommerce.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String login(Model model) {
//		GlobalData.cart.clear();
	    model.addAttribute("cartCount", GlobalData.cart.size());
		return "login";
	}

	@GetMapping("/register")
	public String register(Model model) {
	    model.addAttribute("cartCount", GlobalData.cart.size());
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
	    model.addAttribute("cartCount", GlobalData.cart.size());
		return "index";
	}

		@GetMapping("/default")
		public String defaultAfterLogin(HttpServletRequest request, Model model,Principal principal) {
			model.addAttribute("user",userRepository.findByEmail(principal.getName()));
		    if(request.isUserInRole("ROLE_ADMIN")) {
				return "redirect:/admin/";
			}
		    model.addAttribute("cartCount", GlobalData.cart.size());
		    return "redirect:/user/";
			
		}

	    @GetMapping("/profile")
	    public String getUpdateProfile(Model model,Principal principal,HttpServletRequest request) {
	        User user = userRepository.findByEmail(principal.getName());
	        if(request.isUserInRole("ROLE_ADMIN")) {
	               return "redirect:/admin/profile";
	           }
	           return "redirect:/user/profile";
	        
	    }

}
