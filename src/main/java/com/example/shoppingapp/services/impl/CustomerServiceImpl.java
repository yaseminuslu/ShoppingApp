package com.example.shoppingapp.services.impl;

import com.example.shoppingapp.entities.Customer;
import com.example.shoppingapp.repos.CustomerRepository;
import com.example.shoppingapp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
