package com.example.mybakery.service;

import com.example.mybakery.model.User;
import com.example.mybakery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles("USER") // You can set roles or authorities here based on your application's logic
                .build();
    }
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}