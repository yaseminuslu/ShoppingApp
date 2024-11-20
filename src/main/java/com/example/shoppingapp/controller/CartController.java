package com.example.shoppingapp.controller;

import com.example.shoppingapp.entities.Cart;
import com.example.shoppingapp.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/carts")
public class CartController {
    @Autowired
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @GetMapping("/{customerId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getCart(customerId));
    }

    @PutMapping("/{customerId}/{productId}")
    public Cart updateCart(@PathVariable Long customerId,
                           @PathVariable Long productId,
                           @RequestParam int quantity) {
       return cartService.updateCart(customerId,productId,quantity);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> emptyCart(@PathVariable Long customerId) {
        cartService.emptyCart(customerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{customerId}/add")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long customerId, @RequestParam Long productId, @RequestParam int quantity) {
        cartService.addProductToCart(customerId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{customerId}/remove")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long customerId, @RequestParam Long productId) {
        cartService.removeProductFromCart(customerId, productId);
        return ResponseEntity.noContent().build();
    }
}
