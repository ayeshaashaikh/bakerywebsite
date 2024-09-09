package com.example.mybakery.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<CartItem> items = new ArrayList<>();
    public double getTotalValue() {
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
