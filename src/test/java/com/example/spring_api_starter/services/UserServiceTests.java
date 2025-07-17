package com.example.spring_api_starter.services;

import com.example.spring_api_starter.entities.User;
import com.example.spring_api_starter.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUserById() {
        Optional<User> optionalUser = userRepository.findById(1L);
        assertTrue(optionalUser.isPresent(), "User should be found with ID 1");
        User user = optionalUser.get();
        assertNotNull(user.getName(), "User name should not be null");
    }
}
