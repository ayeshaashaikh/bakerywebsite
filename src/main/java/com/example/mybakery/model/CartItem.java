package com.example.mybakery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")

    private Cart cart;

    private int quantity;
}
