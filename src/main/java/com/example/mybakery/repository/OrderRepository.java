package com.example.mybakery.repository;

import com.example.mybakery.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

