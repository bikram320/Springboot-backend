package com.example.spring_api_starter.repositories;

import com.example.spring_api_starter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean getUserById(Long id);

    Optional<User> findByEmail(String email);
}
