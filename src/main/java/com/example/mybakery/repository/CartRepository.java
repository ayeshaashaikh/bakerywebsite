package com.example.mybakery.repository;

import com.example.mybakery.model.Cart;
import com.example.mybakery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
