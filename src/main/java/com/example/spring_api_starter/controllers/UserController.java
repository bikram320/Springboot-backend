package com.example.spring_api_starter.controllers;

import com.example.spring_api_starter.entities.User;
import com.example.spring_api_starter.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public Iterator<User> getUsers() {
        return userRepository.findAll().iterator();
    }
}
