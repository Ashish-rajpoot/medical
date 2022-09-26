package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecommerce.model.Category;
import com.ecommerce.services.BookServices;
import com.ecommerce.services.CategoryService;

@Controller
public class AdminController {

	@Autowired
	private CategoryService categoryService;
//	private BookServices bookServices;
	
	@GetMapping("/index")
	public String index() {
		return "index";
		
	}
	@GetMapping("/admin")
	public String adminHome() {
		return "adminHome";
		
	}
	@GetMapping("/admin/categories")
	public String getCate(Model model) {
		model.addAttribute("categories",categoryService.getAllCategories());
		return "categories";
	}
	@GetMapping("/admin/categories/add")
	public String getCateAdd(Model model) {
		model.addAttribute("category",new Category());
		return "categoriesAdd";
	}
	@PostMapping("/admin/categories/add")
	public String postCateAdd(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
}
