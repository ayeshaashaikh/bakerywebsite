package com.example.mybakery.controller;

import com.example.mybakery.model.Category;
import com.example.mybakery.model.Product;
import com.example.mybakery.service.CategoryService;
import com.example.mybakery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductCategoryController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products/brownies")
    public String showBrownies(Model model) {
        Category category = categoryService.findByName("Brownies");
        List<Product> products = productService.findByCategoryId(category.getId());
        model.addAttribute("products", products);
        return "brownies";
    }

    @GetMapping("/products/bread")
    public String showBread(Model model) {
        Category category = categoryService.findByName("Bread");
        List<Product> products = productService.findByCategoryId(category.getId());
        model.addAttribute("products", products);
        return "bread";
    }

    @GetMapping("/products/cakes")
    public String showCakes(Model model) {
        Category category = categoryService.findByName("Cakes");
        List<Product> products = productService.findByCategoryId(category.getId());
        model.addAttribute("products", products);
        return "cakes";
    }

    @GetMapping("/products/desserts")
    public String showDesserts(Model model) {
        Category category = categoryService.findByName("Desserts");
        List<Product> products = productService.findByCategoryId(category.getId());
        model.addAttribute("products", products);
        return "desserts";
    }

    @GetMapping("/products/pastries")
    public String showPastries(Model model) {
        Category category = categoryService.findByName("Pastries");
        List<Product> products = productService.findByCategoryId(category.getId());
        model.addAttribute("products", products);
        return "pastries";
    }
}
