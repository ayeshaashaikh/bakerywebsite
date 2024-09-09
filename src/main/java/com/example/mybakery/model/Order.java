package com.example.mybakery.model;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "orders") // Updated table name
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String shippingAddress;
    private String city;
    private String state;
    private String zip;
    private String cardNumber; // In real applications, do not store sensitive payment information
    private String expiryDate; // In real applications, use a payment gateway for processing
    private String cvv; // In real applications, use a payment gateway for processing
    private Double totalValue;

    @ManyToMany
    @JoinTable(name = "order_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<CartItem> items;

    // Getters and setters
}
