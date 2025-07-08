package com.example.spring_api_starter.repositories;

import com.example.spring_api_starter.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
}