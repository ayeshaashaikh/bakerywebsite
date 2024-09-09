package com.example.mybakery.controller;

import com.example.mybakery.dto.CartDTO;
import com.example.mybakery.dto.CartItemDTO;
import com.example.mybakery.dto.ProductDTO;
import com.example.mybakery.model.Cart;
import com.example.mybakery.model.Product;
import com.example.mybakery.model.User;
import com.example.mybakery.service.CartService;
import com.example.mybakery.service.ProductService;
import com.example.mybakery.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        // Fetch User

        Optional<User> optionalUser = Optional.ofNullable(userDetailsService.findById(userId));
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();

        // Fetch Product
        Optional<Product> optionalProduct = productService.findById(productId);
        if (!optionalProduct.isPresent()) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        Product product = optionalProduct.get();

        // Add to Cart
        cartService.addToCart(user, product, quantity);
        return new ResponseEntity<>("Product added to cart", HttpStatus.OK);
    }

    //    @GetMapping("/view")
//    public ResponseEntity<Cart> viewCart() {
//        // Fetch the current authenticated user's username
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        // Find the user by username
//        User user = userDetailsService.findByUsername(username);
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        // Get the cart for the user
//        Cart cart = cartService.getCart(user);
//        if (cart == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(cart, HttpStatus.OK);
//    }
    @GetMapping("/view")
    public String viewCart(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find the user by username
        User user = userDetailsService.findByUsername(username);
        if (user == null) {
            return "error"; // Return an error page or handle appropriately
        }

        // Get the user's cart
        Cart cart = cartService.getCart(user);
        if (cart == null) {
            return "error"; // Return an error page or handle appropriately
        }

        // Convert Cart to CartDTO
        CartDTO cartDTO = convertToCartDTO(cart);
        model.addAttribute("cart", cartDTO);
        return "cart-view"; // Name of your Thymeleaf template
    }

    private CartDTO convertToCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());

        List<CartItemDTO> itemDTOs = cart.getItems().stream().map(item -> {
            CartItemDTO itemDTO = new CartItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setQuantity(item.getQuantity());

            ProductDTO productDTO = new ProductDTO();
            Product product = item.getProduct();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setDescription(product.getDescription()); // Add description if needed

            itemDTO.setProduct(productDTO);
            return itemDTO;
        }).collect(Collectors.toList());

        cartDTO.setItems(itemDTOs);
        cartDTO.setTotalValue(cart.getTotalValue()); // Calculate and add total value
        return cartDTO;
    }
}