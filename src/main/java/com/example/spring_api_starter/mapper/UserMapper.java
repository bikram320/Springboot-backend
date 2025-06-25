package com.example.spring_api_starter.mapper;

import com.example.spring_api_starter.dtos.UserDto;
import com.example.spring_api_starter.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    UserDto toUserDto(User user);
}
