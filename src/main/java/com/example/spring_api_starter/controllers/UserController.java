package com.example.spring_api_starter.controllers;

import com.example.spring_api_starter.dtos.ChangeUserPasswordRequest;
import com.example.spring_api_starter.dtos.RegisterUserRequest;
import com.example.spring_api_starter.dtos.UpdateUserRequest;
import com.example.spring_api_starter.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;


    @GetMapping
    public ResponseEntity<?> getUsers() {
        var users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        var userDto = userService.getUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> registerUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder) {
        var userDto= userService.createUser(request);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        var user= userService.updateUser(request,id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
         userService.deleteUserById(id);
         return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(
            @PathVariable long id , @RequestBody ChangeUserPasswordRequest request)
    {
       userService.changePassword(id , request);
       return ResponseEntity.noContent().build();
    }


}