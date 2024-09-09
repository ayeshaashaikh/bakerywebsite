package com.example.mybakery.repository;

import com.example.mybakery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    Optional<Product> findById(Long id); // Add this method
}
