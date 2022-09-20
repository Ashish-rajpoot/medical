package com.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MedicalResources {

	@GetMapping("/greeting")
	@ResponseBody
	public  String greet(@RequestParam(value = "name", defaultValue = "world") String name) {
		return "hello," + name;
	}
	
	
	
}
