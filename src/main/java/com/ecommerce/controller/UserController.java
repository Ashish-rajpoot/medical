package com.ecommerce.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.ecommerce.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.global.GlobalData;
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
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        List<User> user = userRepository.findAll();
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
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
        return "userOrders";
    }

    @GetMapping("/orders/{id}")
//    @ResponseBody
    public String getAllOrders(@PathVariable("id") int id, Model model, Principal principal) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        List<Order> orders = myOrderRepository.findOrderById(id);
        for(Order order:orders){
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
}