package com.ecommerce.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.global.GlobalData;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.MyOrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.services.CategoryService;
import com.ecommerce.services.ProductService;
import com.ecommerce.util.ImageUtil;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyOrderRepository myOrderRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired ProductService productService;
    @Autowired CategoryService categoryService;

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        List<User> user = userRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("loginUser",userRepository.findByEmail(principal.getName()));
        System.out.println();
        return "home";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal, HttpServletResponse response,HttpServletRequest request) {
//        model.addAttribute("contextPath", request.getContextPath());
        
        model.addAttribute("cartCount", GlobalData.cart.size());
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }

    @GetMapping("/userOrders")
//	   @ResponseBody
    public String getAllOrders(Model model, Principal principal) {
        User user = (userRepository.findByEmail(principal.getName()));
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));
        model.addAttribute("orders", myOrderRepository.findOrderByUserId(user.getId()));
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "userOrders";
    }

    @GetMapping("/orders/{id}")
//    @ResponseBody
    public String getAllOrders(@PathVariable("id") int id, Model model, Principal principal) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        List<Order> orders = myOrderRepository.findOrderById(id);
        for(Order order:orders){
            model.addAttribute("total", order.getOrderProduct().stream().mapToDouble(Product::getPrice).sum());
            model.addAttribute("products", order.getOrderProduct());
        }
        return "orderProducts";
    }

    @GetMapping("/profile")
    public String getUpdateProfile(Model model,Principal principal,HttpSession session) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        User user = userRepository.findByEmail(principal.getName());
            model.addAttribute("user",user);
            session.setAttribute("user", "user");
            System.out.println(user.getRole());
            
            return "profile";
    }
  
    @PostMapping("/profile")
    public String postUpdateProfile(@ModelAttribute("user") User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/user/profile";
    }
    @GetMapping("/product/{imageName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable("imageName") String imageName, Model model) {
         Product image = productService.downloadImage(imageName);
        String imageType = StringUtils.getFilenameExtension(imageName);  
        System.out.println(imageType);
        byte[] imageData =ImageUtil.decompressImage(image.getImageData());
//        System.out.println(image);
//        model.addAttribute("image",image);
//        model.addAttribute("imageType",MediaType.valueOf(imageType));
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/"+imageType))
                    .body(imageData);
    }
    
    
    
//    @PostMapping("/createOrder")
//    @ResponseBody
//    public String createOrder(@RequestBody Map<String, Object> data, Principal principal) throws RazorpayException {
//        System.out.println(data);
//        int amount = Integer.parseInt(data.get("amount").toString());
//        RazorpayClient client = new RazorpayClient("rzp_test_xD1ZcdIlypoUZd", "5l2J2KxH7IT6rGnePue7zmGs");
//
//        JSONObject object = new JSONObject();
//        object.put("amount", amount * 100);
//        object.put("currency", "INR");
//        object.put("receipt", "txn_210427");
//
//        com.razorpay.Order order = client.orders.create(object);
//        System.out.println(order);
//
//        Order myOrder = new Order();
//        ArrayList<Product> cart = GlobalData.cart;
//
//        myOrder.setOrderProduct(cart);
//        myOrder.setOrderId(order.get("id"));
//        myOrder.setAmount(order.get("amount") + "");
//        myOrder.setPaymentId(order.get(null));
//        myOrder.setStatus("created");
//        myOrder.setUser(this.userRepository.findByEmail(principal.getName()));
//
//        myOrder.setReceipt(order.get("receipt"));
//
//        this.myOrderRepository.save(myOrder);
//
//        return order.toString();
//    }
//
//    @PostMapping("/updateOrder")
//    public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data) {
//        Order order = this.myOrderRepository.findByOrderId(data.get("order_id").toString());
//        order.setPaymentId(data.get("payment_id").toString());
//        order.setStatus(data.get("status").toString());
//
//        this.myOrderRepository.save(order);
//        System.out.println(data);
//        GlobalData.cart.clear();
//        return ResponseEntity.ok("success");
//    }

    @GetMapping("/search")
    public String search(@Param("keyword") String keyword, Model model, HttpServletRequest request) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getAllCategories());
        Set<Product> products = productService.getProductByKeyword(keyword);
        if (products.size() <= 0) {
            model.addAttribute("noProduct","No Product Found" );
            return "shop";
        } else {
            if(request.isUserInRole("ROLE_ADMIN")) {
                model.addAttribute("products",products );
                return "products";
            }         
            model.addAttribute("products",products );
            return "shop";
        }
    }

}