package com.example.duckfarm.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.duckfarm.db.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{}
