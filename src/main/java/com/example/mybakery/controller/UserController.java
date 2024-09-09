package com.example.mybakery.controller;

import com.example.mybakery.model.User;
import com.example.mybakery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller

public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterationForm(){
        return "/register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String showLoginForm(){
        return "/login";
    }
}
