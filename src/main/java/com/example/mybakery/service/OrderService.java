package com.example.mybakery.service;

import com.example.mybakery.model.Order;
import com.example.mybakery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository; // Repository for managing orders

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
