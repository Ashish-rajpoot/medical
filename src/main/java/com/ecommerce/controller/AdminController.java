package com.ecommerce.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.MyOrder;
import com.ecommerce.model.Product;
import com.ecommerce.repository.MyOrderRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.services.CategoryService;
import com.ecommerce.services.ProductService;

@Controller
@RequestMapping("/admin/")
public class AdminController {
//	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	public static String uploadDir = "src/main/resources/static/productImages";
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private MyOrderRepository myOrderRepository;
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String adminHome() {
		return "adminHome";
		
	}
	@GetMapping("/categories")
	public String getCate(Model model) {
		model.addAttribute("categories",categoryService.getAllCategories());
		return "categories";
	}
	@GetMapping("/categories/add")
	public String getCateAdd(Model model) {
		model.addAttribute("category",new Category());
		return "categoriesAdd";
	}
	@PostMapping("/categories/add")
	public String postCateAdd(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String getDeleteCate(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	@GetMapping("/categories/update/{id}")
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
	@GetMapping("/products")
	public String getProduct(Model model) {
		model.addAttribute("products",productService.getAllProducts());
		return "products";
	}
//	Product Section
	@GetMapping("/products/add")
	public String getAddProduct(Model model) {
		model.addAttribute("productDTO",new ProductDTO());
		model.addAttribute("categories",categoryService.getAllCategories());
		return "productsAdd";
	};
	@PostMapping("/products/add")
	public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
								@RequestParam("productImage")MultipartFile file,
								@RequestParam("imgName") String imgName)throws IOException{
		Product  product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setQuantity(productDTO.getQuantity());
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
	
	@GetMapping("/product/delete/{id}")
	public String getDeleteProduct(@PathVariable int id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	@GetMapping("/product/update/{id}")
	public String getUpdateProduct(@PathVariable int id, Model model) {
		Product product = productService.getProductById(id).get();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setQuantity(product.getQuantity());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
//		
		model.addAttribute("categories",categoryService.getAllCategories());
		model.addAttribute("productDTO",productDTO);
		return "productsAdd";
	}
	@GetMapping("/orders")
	public String getAllOrders(Model model,Principal principal) {
		List<MyOrder> myOrder = myOrderRepository.findAll();
		model.addAttribute("orders",myOrderRepository.findAll());
		return "orders";
	}
	@GetMapping("/orders/delete/{id}")
	public String getDeleteOrder(@PathVariable int id) {
		myOrderRepository.deleteById(id);
		return "redirect:/admin/orders";
	}
//	@GetMapping("/orders/update/{id}")
//	public String getUpdateOrder(@PathVariable int id, Model model) {
//		Optional<MyOrder> order = myOrderRepository.findById(id);
//		if(order.isPresent()) {
//			model.addAttribute("category",order.get());
//			return "categoriesAdd";
//		}else {
//			return "404";
//		}
//	}
}
























