package com.ecommerce.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.services.CategoryService;
import com.ecommerce.services.ProductService;

@Controller
public class AdminController {
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	
	@Autowired
	private CategoryService categoryService;
//	private BookServices bookServices;
	@Autowired
	private ProductService productService;
	
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
	
	@GetMapping("/admin/categories/delete/{id}")
	public String getDeleteCate(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	@GetMapping("/admin/categories/update/{id}")
	public String getUpdateCate(@PathVariable int id, Model model) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if(category.isPresent()) {
			model.addAttribute("category",category.get());
			return "categoriesAdd";
		}else {
			return "404";
		}
	}
	
//	Product Section
	@GetMapping("/admin/products")
	public String getProduct(Model model) {
		model.addAttribute("products",productService.getAllProducts());
		return "products";
	}
//	Product Section
	@GetMapping("/admin/products/add")
	public String getAddProduct(Model model) {
		model.addAttribute("productDTO",new ProductDTO());
		model.addAttribute("categories",categoryService.getAllCategories());
		return "productsAdd";
	};
	@PostMapping("/admin/products/add")
	public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
								@RequestParam("productImage")MultipartFile file,
								@RequestParam("imgName") String imgName)throws IOException{
		Product  product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		String imgUUID;
		if(!file.isEmpty()) {
			imgUUID = file.getOriginalFilename();
			Path fileAndPathName = Paths.get(uploadDir, imgUUID);
			Files.write(fileAndPathName, file.getBytes());
		}else {
			imgUUID = imgName;
		}
		product.setImageName(imgUUID);
		productService.addProduct(product);
		
		return "redirect:/admin/products";
	}
	
}
























