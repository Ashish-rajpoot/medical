package com.ecommerce.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.global.GlobalData;
import com.ecommerce.model.Category;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
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
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String adminHome(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "adminHome";

    }

    @GetMapping("/categories")
    public String getCate(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/categories/add")
    public String getCateAdd(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/categories/add")
    public String postCateAdd(@ModelAttribute("category") Category category, HttpSession session) {
        try {
            categoryService.addCategory(category);
            session.setAttribute("successMsg", "Category Added successfully...");
            return "redirect:/admin/categories";
        } catch (Exception e) {
            // TODO: handle exception
            session.setAttribute("errorMsg", "Some Thing Went Wrong...");
            return "categoriesAdd";
        }
    }

    @GetMapping("/categories/delete/{id}")
    public String getDeleteCate(@PathVariable int id, HttpSession session) {
        try {
            categoryService.removeCategoryById(id);
            session.setAttribute("successMsg", "Category Removed successfully...");
            return "redirect:/admin/categories";

        } catch (Exception e) {
            // TODO: handle exception
            session.setAttribute("errorMsg", "Some Thing Went Wrong...");
            return "categories";
        }
    }

    @GetMapping("/categories/update/{id}")
    public String getUpdateCate(@PathVariable int id, Model model,HttpSession session) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            session.setAttribute("successMsg", "Category updated successfully...");
            return "categoriesAdd";
        } else {
            return "404";
        }
    }

//	Product Section
    @GetMapping("/products")
    public String getProduct(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

//	Product Section
    @GetMapping("/products/add")
    public String getAddProduct(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "productsAdd";
    };

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
            @RequestParam("productImage") MultipartFile file,
            @RequestParam("imgName") String imgName,
            HttpSession session) throws IOException {

        try {

            Product product = new Product();
            product.setId(productDTO.getId());
            product.setName(productDTO.getName());
            product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
            product.setPrice(productDTO.getPrice());
            product.setQuantity(productDTO.getQuantity());
            product.setDescription(productDTO.getDescription());
            String imgUUID;
            if (!file.isEmpty()) {
                imgUUID = file.getOriginalFilename();
                Path fileAndPathName = Paths.get(uploadDir, imgUUID);
                Files.write(fileAndPathName, file.getBytes());
            } else {
                imgUUID = imgName;
            }
            product.setImageName(imgUUID);
            productService.addProduct(product);

            session.setAttribute("successMsg", "Product added successfully...");
            return "redirect:/admin/products";
        } catch (Exception e) {
            // TODO: handle exception
            session.setAttribute("errorMsg", "Some thing went Wrong...");
            return "productsAdd";
        }
    }

    @GetMapping("/product/delete/{id}")
    public String getDeleteProduct(@PathVariable int id, HttpSession session) {
        try {
            productService.removeProductById(id);
            session.setAttribute("successMsg", "Product removed successfully...");
            return "redirect:/admin/products";
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Some thing went Wrong...");
            return "products";
        }
    }

    @GetMapping("/product/update/{id}")
    public String getUpdateProduct(@PathVariable int id, Model model, HttpSession session) {
        try {

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
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("productDTO", productDTO);
            session.setAttribute("successMsg", "Product Deleted successfully...");
            return "productsAdd";
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Some thing went Wrong...");
            return "products";
        }
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model, Principal principal) {
        List<Order> orders=  myOrderRepository.findAll();
        model.addAttribute("orders", orders);
        for(Order order:orders){
            model.addAttribute("total", order.getOrderProduct().stream().mapToDouble(Product::getPrice).sum());
            model.addAttribute("products", order.getOrderProduct());
        } 
        return "orders";
    }

    @GetMapping("/orders/delete/{id}")
    public String getDeleteOrder(@PathVariable int id, HttpSession session) {
        try {
            myOrderRepository.deleteById(id);
            session.setAttribute("successMsg", "Order Deleted successfully...");
            return "redirect:/admin/orders";
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Some thing went Wrong...");
            return "orders";
        }
    }

    @GetMapping("/orders/{id}")
    public String getAllOrders(@PathVariable("id") int id, Model model, Principal principal) {
        List<Order> orders = myOrderRepository.findOrderById(id);
        for(Order order:orders){
            model.addAttribute("total", order.getOrderProduct().stream().mapToDouble(Product::getPrice).sum());
            model.addAttribute("products", order.getOrderProduct());
        }      
        return "orderProducts";
    }

    // User Section

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @GetMapping("/user/delete/{id}")
    public String getDeleteUser(@PathVariable int id, HttpSession session) {
        try {
            userRepository.deleteById(id);
            session.setAttribute("successMsg", "User Deleted successfully...");
            return "redirect:/admin/users";

        } catch (Exception e) {
            session.setAttribute("errorMsg", "Some thing went wrong...");
            return "users";
            // TODO: handle exception
        }
    }

    @GetMapping("/user/update/{id}")
    public String getUpdateUser(@PathVariable int id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "updateUser";
        } else {
            return "404";
        }
    }

    @PostMapping("/user/update")
    public String postUpdateUser(@ModelAttribute("user") User user, HttpSession session) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            session.setAttribute("successMsg", "User Updated successfully...");
            return "redirect:/admin/users";
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Some thing went wrong...");
            return "updateUser";
        }
    }

    @GetMapping("/profile")
    public String getUpdateProfile(Model model, Principal principal, HttpSession session) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);
        session.setAttribute("admin", "admin");
        System.out.println(user.getRole());

        return "profile";
    }

    @PostMapping("/profile")
    public String postUpdateProfile(HttpSession session, HttpServletRequest request,
            @ModelAttribute("user") User user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            session.setAttribute("successMsg", "Profile Updated successfully...");
            return "redirect:/admin/profile";

        } catch (Exception e) {
            session.setAttribute("errorMsg", "Some thing went wrong...");
            return "profile";
        }
    }
}
