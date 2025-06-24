package com.example.spring_api_starter.repositories;

import com.example.spring_api_starter.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
