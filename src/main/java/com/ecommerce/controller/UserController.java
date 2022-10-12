package com.ecommerce.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.ecommerce.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/")
    public String index(Model model) {
        List<User> user = userRepository.findAll();
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);
        System.out.println("checkout page" + user.getFirstName());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }

    @GetMapping("/userOrders")
//	   @ResponseBody
    public String getAllOrders(Model model, Principal principal) {
        User user = (userRepository.findByEmail(principal.getName()));
        System.out.println(user.getId());
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));
        model.addAttribute("orders", myOrderRepository.findOrderByUserId(user.getId()));
        return "userOrders";
    }

    @GetMapping("/orders/{id}")
//    @ResponseBody
    public String getAllOrders(@PathVariable("id") int id, Model model, Principal principal) {
        List<Order> orders = myOrderRepository.findOrderById(id);
        System.out.println("order size:" + orders.size());
        for(Order order:orders){
            model.addAttribute("products", order.getOrderProduct());
//            Set<Product> products = order.getOrderProduct();
//            System.out.println(products.size());
//            for(Product product : products){
////                System.out.println(product.getId());
//                System.out.println(  product.getName());
////                Set<Product> orderProducts = productRepository.findAllById(product.getId());
////                System.out.println(orderProducts.size());
////                for(Product orderProduct : orderProducts){
////                    System.out.println(orderProduct.getId());
////                }
//                }
        }
        return "orderProducts";
    }

}