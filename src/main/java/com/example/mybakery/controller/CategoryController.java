package com.example.mybakery.controller;

import com.example.mybakery.model.Category;
import com.example.mybakery.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories/list";
    }

    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/add";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id)));
        return "categories/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable("id") Long id, @ModelAttribute Category category) {
        category.setId(id);
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }
}
