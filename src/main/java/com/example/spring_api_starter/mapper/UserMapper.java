package com.example.spring_api_starter.mapper;

import com.example.spring_api_starter.dtos.ProductDto;
import com.example.spring_api_starter.dtos.RegisterUserRequest;
import com.example.spring_api_starter.dtos.UpdateUserRequest;
import com.example.spring_api_starter.dtos.UserDto;
import com.example.spring_api_starter.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    User toUser(RegisterUserRequest request);
    void updateUser(UpdateUserRequest request , @MappingTarget User user);
}