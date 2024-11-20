package com.example.shoppingapp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "order_item")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    private int quantity;
    private double priceAtOrder;
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;
}
