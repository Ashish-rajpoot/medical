package com.ecommerce.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.ecommerce.model.Order;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.global.GlobalData;
import com.ecommerce.model.Product;
import com.ecommerce.repository.MyOrderRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.services.CategoryService;
import com.ecommerce.services.ProductService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Controller
//@RequestMapping("/user/")
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MyOrderRepository myOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getAllCategories());
        List<Product> products = productService.getAllProducts();
        if (products.size() <= 0) {
            model.addAttribute("noProduct","No Product Found" );
            return "shop";
        } else {
            model.addAttribute("products",products );
            return "shop";
        }
    }

    @GetMapping("/shop/category/{id}")
    public String getProductByCategoryId(Model model, @PathVariable("id") int id) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProductByCategoryId(id));
        boolean checkProduct= productService.getAllProductByCategoryId(id).isEmpty();
        if(checkProduct)
        model.addAttribute("noProduct","No Product Found" );
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable("id") int id) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("product", productService.getProductById(id).get());
        return "viewProduct";
    }

    @PostMapping("/createOrder")
    @ResponseBody
    public String createOrder(@RequestBody Map<String, Object> data, Principal principal) throws RazorpayException {
        System.out.println(data);
        int amount = Integer.parseInt(data.get("amount").toString());
        RazorpayClient client = new RazorpayClient("rzp_test_xD1ZcdIlypoUZd", "5l2J2KxH7IT6rGnePue7zmGs");

        JSONObject object = new JSONObject();
        object.put("amount", amount * 100);
        object.put("currency", "INR");
        object.put("receipt", "txn_210427");

        com.razorpay.Order order = client.orders.create(object);
        System.out.println(order);

        Order myOrder = new Order();
        ArrayList<Product> cart = GlobalData.cart;

        myOrder.setOrderProduct(cart);
        myOrder.setOrderId(order.get("id"));
        myOrder.setAmount(order.get("amount") + "");
        myOrder.setPaymentId(order.get(null));
        myOrder.setStatus("created");
        myOrder.setUser(this.userRepository.findByEmail(principal.getName()));

        myOrder.setReceipt(order.get("receipt"));

        this.myOrderRepository.save(myOrder);

        return order.toString();
    }

    @PostMapping("/updateOrder")
    public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data) {
        Order order = this.myOrderRepository.findByOrderId(data.get("order_id").toString());
        order.setPaymentId(data.get("payment_id").toString());
        order.setStatus(data.get("status").toString());

        this.myOrderRepository.save(order);
        System.out.println(data);
        GlobalData.cart.clear();
        return ResponseEntity.ok("success");
    }

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
