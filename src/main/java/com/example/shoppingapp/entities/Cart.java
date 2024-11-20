package com.example.shoppingapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Table(name = "cart")
@Entity
@Getter
@Setter
@NoArgsConstructor

@AllArgsConstructor
public class Cart extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private Set<CartItem> cartItems = new HashSet<>();


    @Column(name = "total_price", nullable = false)
    private double totalPrice;
}
