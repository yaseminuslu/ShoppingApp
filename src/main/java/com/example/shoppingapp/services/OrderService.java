package com.example.shoppingapp.services;

import com.example.shoppingapp.entities.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long customerId);
    Order getOrderForCode(String orderCode);
    List<Order> getAllOrdersForCustomer(Long customerId);
}
