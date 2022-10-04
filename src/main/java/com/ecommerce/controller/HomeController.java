package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.services.CategoryService;
import com.ecommerce.services.ProductService;

@Controller
public class HomeController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("categories",categoryService.getAllCategories());
		model.addAttribute("products",productService.getAllProducts());
		return "shop";
	}
	@GetMapping("/shop/category/{id}")
	public String getProductByCategoryId(Model model, @PathVariable ("id") int id) {
		model.addAttribute("categories",categoryService.getAllCategories());
		model.addAttribute("products",productService.getAllProductByCategoryId(id));
		return "shop";
	}
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model, @PathVariable int id) {
		model.addAttribute("product",productService.getProductById(id).get());
		return "viewProduct";
	}
	
}
