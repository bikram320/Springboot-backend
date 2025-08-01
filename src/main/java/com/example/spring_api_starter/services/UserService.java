package com.example.spring_api_starter.services;

import com.example.spring_api_starter.dtos.ChangeUserPasswordRequest;
import com.example.spring_api_starter.dtos.RegisterUserRequest;
import com.example.spring_api_starter.dtos.UpdateUserRequest;
import com.example.spring_api_starter.dtos.UserDto;
import com.example.spring_api_starter.exceptions.DuplicateDataException;
import com.example.spring_api_starter.exceptions.ResourceNotFoundException;
import com.example.spring_api_starter.mapper.UserMapper;
import com.example.spring_api_starter.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Iterable<UserDto> getUsers(){
        return userRepository.findAll(/*Sort.by(sortBy)*/)
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public UserDto getUserById(Long id){
        var user= userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found by given data :"+id));

        return userMapper.toUserDto(user);

    }

    public UserDto createUser(RegisterUserRequest request ){
        if (userRepository.existsByEmail(request.getEmail())){
           throw  new DuplicateDataException("Email is Already Registered "+request.getEmail());
        }


        var newUser = userMapper.toUser(request);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);

        return userMapper.toUserDto(newUser);

    }

    public UserDto updateUser(@Valid UpdateUserRequest request , long id){
        var user = userRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("User not found by given data :"+id));
        userMapper.updateUser(request, user);
        userRepository.save(user);
        return  userMapper.toUserDto(user);
    }

    public void deleteUserById(Long id){
        var user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found by given data :"+id));
        userRepository.delete(user);
    }

    public void changePassword(Long id , ChangeUserPasswordRequest request){
        var user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found by given data :"+id));
        if(!user.getPassword().equals(request.getOldPassword())){
            throw new InputMismatchException("Old password does not match");
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }
}
