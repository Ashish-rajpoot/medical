package com.ecommerce.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.global.GlobalData;
import com.ecommerce.services.CategoryService;
import com.ecommerce.services.ProductService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Controller
//@RequestMapping("/user/")
public class HomeController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("categories",categoryService.getAllCategories());
		model.addAttribute("products",productService.getAllProducts());
		return "shop";
	}
	@GetMapping("/shop/category/{id}")
	public String getProductByCategoryId(Model model, @PathVariable ("id") int id) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("categories",categoryService.getAllCategories());
		model.addAttribute("products",productService.getAllProductByCategoryId(id));
		return "shop";
	}
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model, @PathVariable int id) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("product",productService.getProductById(id).get());
		return "viewProduct";
	}
	
	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder(@RequestBody Map<String, Object> data) throws RazorpayException {
		System.out.println(data);
		int amount = Integer.parseInt(data.get("amount").toString());
		RazorpayClient client =  new RazorpayClient("rzp_test_xD1ZcdIlypoUZd", "5l2J2KxH7IT6rGnePue7zmGs");
		
		JSONObject object = new JSONObject();
		object.put("amount", amount*100);
		object.put("currency","INR");
		object.put("receipt","txn_210427" );
		
		Order order = client.orders.create(object);
		System.out.println(order);
		return order.toString();
	}
	
}
