package com.example.mybakery.controller;

import com.example.mybakery.model.Category;
import com.example.mybakery.model.Product;
import com.example.mybakery.service.CategoryService;
import com.example.mybakery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String listProducts(@RequestParam(name = "categoryId", required = false) Long categoryId, Model model) {
        List<Product> products;
        if (categoryId != null && categoryId > 0) {
            products = productService.findByCategoryId(categoryId);
        } else {
            products = productService.findAll();
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryId", categoryId); // Add this line to keep track of selected category
        return "products/list"; // Make sure this matches the name of your Thymeleaf template
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "products/add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id)));
        model.addAttribute("categories", categoryService.findAll());
        return "products/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute Product product) {
        product.setId(id);
        productService.save(product);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/admin/products/list";
    }
    @GetMapping("/filter")
    public String filterProductsByCategory(@RequestParam Long categoryId, Model model) {
        List<Product> products = productService.findByCategoryId(categoryId);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll()); // To show category dropdown
        return "products/list"; // Name of the view that will show the list of products
    }

}
