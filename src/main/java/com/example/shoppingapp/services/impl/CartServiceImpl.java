package com.example.shoppingapp.services.impl;

import com.example.shoppingapp.entities.Cart;
import com.example.shoppingapp.entities.CartItem;
import com.example.shoppingapp.entities.Customer;
import com.example.shoppingapp.entities.Product;
import com.example.shoppingapp.exception.CartNotFoundException;
import com.example.shoppingapp.exception.CustomerNotFoundException;
import com.example.shoppingapp.exception.ProductNotFoundException;
import com.example.shoppingapp.repos.CartRepository;
import com.example.shoppingapp.repos.CustomerRepository;
import com.example.shoppingapp.repos.ProductRepository;
import com.example.shoppingapp.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CustomerRepository customerRepository;
    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Cart getCart(Long customerId) {
        return cartRepository.findByCustomer_Id(customerId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
    }

    @Override
    public Cart updateCart(Long customerId, Long productId, int newQuantity) {
        Cart cart = cartRepository.findByCustomer_Id(customerId)
                .orElseThrow(() -> new CartNotFoundException("Sepet bulunamadı"));

        // Sepetteki ürünü bul ve adedini güncelle
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getProduct().getId().equals(productId)) {
                cartItem.setQuantity(newQuantity);
                // Ürünün yeni fiyatını hesapla
                cartItem.setItemPrice(cartItem.getProduct().getPrice() * newQuantity);
                break;
            }
        }
        updateTotalPrice(cart);
        return cartRepository.save(cart);
    }

    private void updateTotalPrice(Cart cart) {
        double total = 0;
        for (CartItem cartItem : cart.getCartItems()) {
            total += cartItem.getItemPrice();
        }
        cart.setTotalPrice(total);
    }

    @Override
    public void emptyCart(Long customerId) {
        Cart cart = getCart(customerId);
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }

    @Override
    public void addProductToCart(Long customerId, Long productId, int quantity) {
        Cart cart = cartRepository.findByCustomer_Id(customerId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();

                    Customer customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
                    newCart.setCustomer(customer);
                    newCart.setTotalPrice(0);
                    newCart.setCartItems(Set.of());
                    return cartRepository.save(newCart);
                });

        // Ürün kontrolü
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Sepette ürün var mı
        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            // Mevcut ürünün miktarını artır ve fiyatı güncelle
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            existingCartItem.setItemPrice(existingCartItem.getQuantity() * product.getPrice());
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setItemPrice(product.getPrice() * quantity); // Fiyat hesaplanıyor
            cart.getCartItems().add(newCartItem); // Sepete yeni ürün ekleniyor
        }
        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(Long customerId, Long productId) {
        Cart cart = getCart(customerId);
        // ürünü bul
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found in cart"));

        cart.getCartItems().remove(cartItem);
        updateTotalPrice(cart);
        cartRepository.save(cart);
    }
}
