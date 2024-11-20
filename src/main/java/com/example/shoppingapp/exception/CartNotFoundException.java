package com.example.shoppingapp.exception;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException(String message) {
        super(message);
    }
}
