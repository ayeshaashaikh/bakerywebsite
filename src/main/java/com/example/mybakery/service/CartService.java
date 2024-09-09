package com.example.mybakery.service;

import com.example.mybakery.model.Cart;
import com.example.mybakery.model.CartItem;
import com.example.mybakery.model.Product;
import com.example.mybakery.model.User;
import com.example.mybakery.repository.CartRepository;
import com.example.mybakery.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public void addToCart(User user, Product product, int quantity) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        CartItem cartItem = cartItemRepository.findByProduct(product);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cart.getItems().add(cartItem);
        cartRepository.save(cart);
    }

    @Transactional
    public Cart getCart(User user) {
        return cartRepository.findByUser(user);
    }
    public void clearCart(User user) {
        // Fetch the user's cart
        Cart cart = getCart(user);
        if (cart != null) {
            // Remove all items from the cart
            cartItemRepository.deleteAll(cart.getItems());

            // Optionally, you might want to also delete the cart itself
            cartRepository.delete(cart);
        }
    }
}
