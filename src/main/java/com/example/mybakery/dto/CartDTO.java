package com.example.mybakery.dto;

import lombok.Data;

import java.util.List;

@Data

    public class CartDTO {
        private Long id;
        private List<CartItemDTO> items;
    private double totalValue;


    // Constructors, getters, and setters
    }

