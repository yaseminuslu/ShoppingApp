package com.example.shoppingapp.services.impl;

import com.example.shoppingapp.entities.*;
import com.example.shoppingapp.exception.CartNotFoundException;
import com.example.shoppingapp.exception.OrderNotFoundException;
import com.example.shoppingapp.repos.OrderRepository;
import com.example.shoppingapp.services.CartService;
import com.example.shoppingapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    @Override
    public Order placeOrder(Long customerId) {
        Cart cart = cartService.getCart(customerId);

        if (cart.getCartItems().isEmpty()) {
            throw new CartNotFoundException("Cart is empty");
        }
        // Stok kontrolü
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
        }
        // Sipariş oluştur
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setOrderCode(UUID.randomUUID().toString());
        order.setOrderItems(cart.getCartItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtOrder(cartItem.getProduct().getPrice());  // Sipariş anındaki fiyat
            orderItem.setTotalPrice(cartItem.getItemPrice());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toSet()));

        // Sipariş toplam fiyatını hesapla
        double totalPrice = order.getOrderItems().stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
        order.setTotalPrice(totalPrice);

        // Sepeti boşalt ve stokları güncelle
        cartService.emptyCart(customerId);
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            product.decreaseStock(orderItem.getQuantity());
        }
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderForCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Override
    public List<Order> getAllOrdersForCustomer(Long customerId) {
        List<Order> orders = orderRepository.findAllByCustomer_Id(customerId);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for customer with ID: " + customerId);
        }
        return orders;
    }
}
