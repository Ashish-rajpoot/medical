package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.global.GlobalData;
import com.ecommerce.model.Product;
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
	
	@GetMapping("/checkout")
	public String checkout(Model model) {
		model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		return "checkout";
	}
	
//	@PostMapping("/createOrder")
//	@ResponseBody
//	public String createOrder() {
//		System.out.println("hey order excuted");
//		return "done";
//	}
	
}
