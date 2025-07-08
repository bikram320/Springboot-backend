package com.example.spring_api_starter.mapper;

import com.example.spring_api_starter.dtos.CartDto;
import com.example.spring_api_starter.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source="id" , target = "id")
    CartDto toCartDto(Cart cart);
}
