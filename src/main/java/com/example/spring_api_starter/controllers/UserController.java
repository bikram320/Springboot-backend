package com.example.spring_api_starter.controllers;

import com.example.spring_api_starter.dtos.UserDto;
import com.example.spring_api_starter.entities.User;


import com.example.spring_api_starter.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;


    @GetMapping
    public Iterable<UserDto> getUsers(
            @RequestHeader (required = false , name = "x-auth-token")  String token,
            @RequestParam(required = false , defaultValue = "" ,name = "sort") String sortBy) {

        System.out.println(token);

        if (!Set.of("name","email").contains(sortBy))
            sortBy = "name";
        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
                .toList();

    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user= userRepository.findById(id).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        var userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.ok(userDto);
    }
}
