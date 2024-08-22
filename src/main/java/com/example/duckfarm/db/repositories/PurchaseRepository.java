package com.example.duckfarm.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.duckfarm.db.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{}
