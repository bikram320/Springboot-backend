package com.example.spring_api_starter.repositories;

import com.example.spring_api_starter.entities.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT C FROM Cart C WHERE C.id= :cartId")
    Optional<Cart> getCartWithItems(@Param("cartId") UUID cartId);
}