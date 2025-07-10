package com.example.spring_api_starter.services;

import com.example.spring_api_starter.dtos.ChangeUserPasswordRequest;
import com.example.spring_api_starter.dtos.RegisterUserRequest;
import com.example.spring_api_starter.dtos.UpdateUserRequest;
import com.example.spring_api_starter.dtos.UserDto;
import com.example.spring_api_starter.mapper.UserMapper;
import com.example.spring_api_starter.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Iterable<UserDto> getUsers(){
        return userRepository.findAll(/*Sort.by(sortBy)*/)
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public ResponseEntity<?> getUserById(Long id){
        var user= userRepository.findById(id).orElse(null);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.toUserDto(user), HttpStatus.OK);

    }

    public ResponseEntity<?> createUser(@Valid RegisterUserRequest request , UriComponentsBuilder uriBuilder){
        if (userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body(
                    Map.of("email","email is already registered")
            );
        }
        var newUser = userMapper.toUser(request);
        userRepository.save(newUser);

        UserDto userDto = userMapper.toUserDto(newUser);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    public ResponseEntity<?> updateUser(@Valid UpdateUserRequest request , long id){
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.updateUser(request, user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    public ResponseEntity<?> deleteUserById(Long id){
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> changePassword(Long id , ChangeUserPasswordRequest request){
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if(!user.getPassword().equals(request.getOldPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }
}
