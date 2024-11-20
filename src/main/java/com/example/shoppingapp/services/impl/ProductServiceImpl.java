package com.example.shoppingapp.services.impl;

import com.example.shoppingapp.entities.Product;
import com.example.shoppingapp.exception.ProductNotFoundException;
import com.example.shoppingapp.repos.ProductRepository;
import com.example.shoppingapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
    }
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        Product existProduct=productRepository.findById(productId).orElseThrow(()->
                new ProductNotFoundException("Product Not Found"));
        existProduct.setName(product.getName());
        existProduct.setPrice(product.getPrice());
        existProduct.setStock(product.getStock());
        return productRepository.save(existProduct);
    }

    @Override
    public boolean deleteProduct(Long productId) {
        Product product= productRepository.findById(productId).orElseThrow(()->
                new ProductNotFoundException("Product Not Found"));
        productRepository.delete(product);
        return true;
    }
}
