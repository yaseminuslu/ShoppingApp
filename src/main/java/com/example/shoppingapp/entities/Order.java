package com.example.shoppingapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Table(name = "orders")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity  {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems;

    private double totalPrice;
    private String orderCode;
}
