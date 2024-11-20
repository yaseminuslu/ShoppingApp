package com.example.shoppingapp.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Table(name = "customer")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname",nullable = false)
    private String surname;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "phoneNumber", unique = true, nullable = false)
    private String phoneNumber;

    @OneToOne(mappedBy = "customer")
    @JsonManagedReference
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Order> orders;
}
