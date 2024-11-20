package com.example.shoppingapp.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Table(name = "product")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "price",nullable = false)
    private double price;
    @Column(name = "stock",nullable = false)
    private int stock;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems;

    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        this.stock -= quantity;
    }
}
