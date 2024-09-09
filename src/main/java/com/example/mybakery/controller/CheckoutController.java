package com.example.mybakery.controller;

import com.example.mybakery.model.Cart;
import com.example.mybakery.model.Order;
import com.example.mybakery.model.User;
import com.example.mybakery.repository.UserRepository;
import com.example.mybakery.service.CartService;
import com.example.mybakery.service.OrderService;
import com.example.mybakery.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CheckoutController {

    @Autowired
    private OrderService orderService; // Service to handle order-related operations

    @Autowired
    private CartService cartService; // Service to fetch cart details
    @Autowired
    UserDetailsServiceImpl userDetailsService ;
    @GetMapping("/checkout")
    public String showCheckoutPage(Model model) {
        // Add any needed data to the model, if required
        return "checkout-page"; // Thymeleaf template for the checkout page
    }

    @PostMapping("/checkout/submit")
    public ModelAndView submitCheckout(@RequestParam String address, @RequestParam String city,
                                       @RequestParam String state, @RequestParam String zip,
                                       @RequestParam String cardNumber, @RequestParam String expiryDate,
                                       @RequestParam String cvv) {

        // Fetch the current authenticated user's username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userDetailsService.findByUsername(username);
        if (user == null) {
            return new ModelAndView("error", "message", "User not found");
        }

        // Fetch the user's cart
        Cart cart = cartService.getCart(user);
        if (cart == null || cart.getItems().isEmpty()) {
            return new ModelAndView("error", "message", "Your cart is empty");
        }

        // Create and save the order
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(address);
        order.setCity(city);
        order.setState(state);
        order.setZip(zip);
        order.setCardNumber(cardNumber); // In real applications, do not store sensitive payment information
        order.setExpiryDate(expiryDate); // In real applications, use a payment gateway for processing
        order.setCvv(cvv); // In real applications, use a payment gateway for processing
        order.setTotalValue(cart.getTotalValue());
        order.setItems(cart.getItems());

        orderService.saveOrder(order);

        // Clear the cart
        cartService.clearCart(user);

        // Redirect to order confirmation page
        return new ModelAndView("order-confirmation", "order", order);
    }
}
