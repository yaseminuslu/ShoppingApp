package com.example.shoppingapp.controller;

import com.example.shoppingapp.entities.Order;
import com.example.shoppingapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<Order> placeOrder(@PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(customerId));
    }

    @GetMapping("/{orderCode}")
    public ResponseEntity<Order> getOrderByCode(@PathVariable String orderCode) {
        return ResponseEntity.ok(orderService.getOrderForCode(orderCode));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getAllOrdersForCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getAllOrdersForCustomer(customerId));
    }
}
