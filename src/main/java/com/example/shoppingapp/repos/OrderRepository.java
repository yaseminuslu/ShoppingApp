package com.example.shoppingapp.repos;

import com.example.shoppingapp.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByOrderCode(String orderCode);
    List<Order> findAllByCustomer_Id(Long customerId);
}
