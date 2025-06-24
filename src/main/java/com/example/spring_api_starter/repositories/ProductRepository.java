package com.example.spring_api_starter.repositories;

import com.example.spring_api_starter.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
