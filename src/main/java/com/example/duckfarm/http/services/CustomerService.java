package com.example.duckfarm.http.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.duckfarm.db.model.Customer;
import com.example.duckfarm.db.repositories.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer findById(Long id) {

        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.");
        }

        return customer.get();
    }

}
