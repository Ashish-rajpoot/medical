package com.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.global.GlobalData;
import com.ecommerce.model.MyOrder;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.MyOrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MyOrderRepository myOrderRepository;

	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/")
	public String index(Model model) {
		List<User> user = userRepository.findAll();
		model.addAttribute("user", user);
		return "home";
	}

	@GetMapping("/checkout")
	public String checkout(Model model, Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("user",user);
		System.out.println("checkout page" + user.getFirstName() );
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		return "checkout";
	}
	
	   @GetMapping("/orders")
//	   @ResponseBody
	    public String getAllOrders(Model model,Principal principal) {
	       User user = (userRepository.findByEmail(principal.getName()));
	       System.out.println(user.getId());
//	         myOrder = myOrderRepository.findOrderByUserId(user.getId());
//	        model.addAttribute("orders",myOrderRepository.findOrderByUser(user.getId()));
	       model.addAttribute("orders",myOrderRepository.findOrderByUserId(user.getId()));
//	        model.addAttribute("orders",myOrderRepository.findOrderByUser(principal.getName()));
//	        List<MyOrder> orders = myOrderRepository.findAll();
//	        for (MyOrder myOrder : orders) {
//	          List<Product>  product=myOrder.getProduct();
//	          model.addAttribute("product",product);
//	          
//	              for (Product rpoProduct : product) {
//                   model.addAttribute("products",rpoProduct);
//                }
//
//            }
	        return "orders";
	    }
	   @GetMapping("/orders/{id}")
@ResponseBody
	   public String getAllOrders(@PathVariable ("id") int id, Model model,Principal principal) {
	       
	       List<Product> products = myOrderRepository.findAllProductsByorderId(id);
	       System.out.println(products);
//	       for (MyOrder myOrder : order) {
//	           for (Product orderProduct : myOrder.getProduct()) {
//	       model.addAttribute("products",orderProduct);
//            }
//        }
	       return "orderProducts";
	   }
	   
	   
	   
	   

//	@PostMapping("/createOrder")
//	@ResponseBody
//	public String createOrder() {
//		System.out.println("hey order excuted");
//		return "done";
//	}

//	@PostMapping("/createOrder")
//	@ResponseBody
//	public String createOrder(@RequestBody Map<String, Object> data, Principal principal) throws RazorpayException {
//		System.out.println(data);
//		int amount = Integer.parseInt(data.get("amount").toString());
//		RazorpayClient client = new RazorpayClient("rzp_test_xD1ZcdIlypoUZd", "5l2J2KxH7IT6rGnePue7zmGs");
//
//		JSONObject object = new JSONObject();
//		object.put("amount", amount * 100);
//		object.put("currency", "INR");
//		object.put("receipt", "txn_210427");
//
//		Order order = client.orders.create(object);
//		System.out.println(order);
//
//		MyOrder myOrder = new MyOrder();
//		myOrder.setOrderId(order.get("id"));
//		myOrder.setAmount(order.get("amount") + "");
//		myOrder.setPayment(order.get(null));
//		myOrder.setStatus("created");
//		myOrder.setUser(this.userRepository.findByEmail(principal.getName()));
//		myOrder.setReceipt(order.get("receipt"));
//
//		this.myOrderRepository.save(myOrder);
//
//		return order.toString();
//	}
//
//	@PostMapping("/updateOrder")
//	public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data) {
//		System.out.println(data);
//		return ResponseEntity.ok("");
//	}
}
