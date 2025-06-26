package com.example.spring_api_starter.repositories;

import com.example.spring_api_starter.entities.Category;
import com.example.spring_api_starter.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Byte id);

    @EntityGraph(attributePaths = "category")
    @Query("select  p from Product p")
    List<Product> findAllWithCategory();
}
