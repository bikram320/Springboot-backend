package com.example.spring_api_starter.repositories;

import com.example.spring_api_starter.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
