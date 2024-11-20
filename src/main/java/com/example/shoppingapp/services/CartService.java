package com.example.shoppingapp.services;

import com.example.shoppingapp.entities.Cart;

public interface CartService {
    Cart getCart(Long customerId);
    Cart updateCart(Long customerId, Long productId, int quantity);
    void emptyCart(Long customerId);
    void addProductToCart(Long customerId, Long productId, int quantity);
    void removeProductFromCart(Long customerId, Long productId);
}
