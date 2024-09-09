package com.example.mybakery.service;

import com.example.mybakery.model.Product;
import com.example.mybakery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

 public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
