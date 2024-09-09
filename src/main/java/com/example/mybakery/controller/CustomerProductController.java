package com.example.mybakery.controller;

import com.example.mybakery.model.Category;
import com.example.mybakery.model.Product;
import com.example.mybakery.model.User;
import com.example.mybakery.service.CategoryService;
import com.example.mybakery.service.ProductService;
import com.example.mybakery.service.UserDetailsServiceImpl;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller

public class CustomerProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserDetailsServiceImpl userService;
//    @GetMapping("/products")
//    public String listProducts(@RequestParam(name = "categoryId", required = false) Long categoryId, Model model) {
//        List<Product> products;
//        if (categoryId != null && categoryId > 0) {
//            products = productService.findByCategoryId(categoryId);
//        } else {
//            products = productService.findAll();
//        }
//        List<Category> categories = categoryService.findAll();
//        model.addAttribute("products", products);
//        model.addAttribute("categories", categories);
//        model.addAttribute("selectedCategoryId", categoryId); // Add this line to keep track of selected category
//        return "products"; // Make sure this matches the name of your Thymeleaf template
//    }
@GetMapping("/products")
public String listProducts(@RequestParam(name = "categoryId", required = false) Long categoryId, Model model) {
    // Fetch products based on category
    List<Product> products;
    if (categoryId != null && categoryId > 0) {
        products = productService.findByCategoryId(categoryId);
    } else {
        products = productService.findAll();
    }

    // Fetch all categories
    List<Category> categories = categoryService.findAll();

    // Get the current logged-in user's username
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    // Fetch the user based on username
    User user = userService.findByUsername(username);

    // Add attributes to the model
    model.addAttribute("products", products);
    model.addAttribute("categories", categories);
    model.addAttribute("selectedCategoryId", categoryId);
    model.addAttribute("user", user); // Add user object to the model

    // Return the name of the Thymeleaf template
    return "products";
}

    // Method to get the current logged-in user
   

    @GetMapping("/customfilter")
    public String customerFilterProductsByCategory(@RequestParam Long categoryId, Model model) {
        List<Product> products = productService.findByCategoryId(categoryId);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll()); // To show category dropdown
        return "products"; // Name of the view that will show the list of products
    }

}
