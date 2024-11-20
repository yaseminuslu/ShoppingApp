package com.example.shoppingapp.services;

import com.example.shoppingapp.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProduct(Long productId);
    Product createProduct(Product product);
    Product updateProduct(Product product, Long productId);
    boolean deleteProduct(Long productId);
}
