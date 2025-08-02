package com.example.spring_api_starter.mapper;

import com.example.spring_api_starter.dtos.CartDto;
import com.example.spring_api_starter.dtos.CartItemDto;
import com.example.spring_api_starter.entities.Cart;
import com.example.spring_api_starter.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

//    @Mapping(source="id" , target = "id")
//    @Mapping(target = "items" , source = "items")
//    @Mapping(target ="totalPrice" , expression = "java(cart.getTotalPrice())")
    CartDto toCartDto(Cart cart);

//    @Mapping(source = "product", target = "product")
//    @Mapping(source = "quantity", target = "quantity")
//    @Mapping(target = "totalPrice" ,expression = "java(cartItem.getTotalPrice())")
    CartItemDto toCartDto(CartItem cartItem);
}
