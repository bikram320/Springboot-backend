package com.example.spring_api_starter.services;

import com.example.spring_api_starter.entities.User;
import com.example.spring_api_starter.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AllArgsConstructor
public class UserServiceTests {

    private UserRepository userRepository;

    @Test
    public void testGetUserById() {
        Optional<User> optionalUser = userRepository.findById(1L);
        assertTrue(optionalUser.isPresent(), "User should be found with ID 1");
        User user = optionalUser.get();
        assertNotNull(user.getName(), "User name should not be null");
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setName("Test");
        userRepository.save(user);
    }
}
