package com.example.mybakery.repository;

import com.example.mybakery.model.CartItem;
import com.example.mybakery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByProduct(Product product);
}
