package com.example.spring_api_starter.controllers;

import com.example.spring_api_starter.dtos.RegisterUserRequest;
import com.example.spring_api_starter.dtos.UpdateUserRequest;
import com.example.spring_api_starter.dtos.UserDto;
import com.example.spring_api_starter.entities.User;


import com.example.spring_api_starter.mapper.UserMapper;
import com.example.spring_api_starter.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;


@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @GetMapping
    public Iterable<UserDto> getUsers(
            @RequestHeader (required = false , name = "x-auth-token")  String token,
            @RequestParam(required = false , defaultValue = "" ,name = "sort") String sortBy) {

        System.out.println(token);

        if (!Set.of("name","email").contains(sortBy))
            sortBy = "name";
        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toUserDto)
                .toList();

    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user= userRepository.findById(id).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toUserDto(user));
    }
    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder) {
        var newUser = userMapper.toUser(request);
        userRepository.save(newUser);

        UserDto userDto = userMapper.toUserDto(newUser);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.updateUser(request, user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

}
