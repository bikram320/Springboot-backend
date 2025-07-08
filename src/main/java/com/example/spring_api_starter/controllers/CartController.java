package com.example.spring_api_starter.controllers;

import com.example.spring_api_starter.dtos.CartDto;
import com.example.spring_api_starter.entities.Cart;
import com.example.spring_api_starter.mapper.CartMapper;
import com.example.spring_api_starter.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController()
@AllArgsConstructor
@RequestMapping("/carts")
public class CartController {


    private final CartRepository cartRepository;
    private final CartMapper cartMapper;


    @PostMapping
    public ResponseEntity<?> createCart(UriComponentsBuilder uriBuilder) {
        var cart = new Cart();
        cartRepository.save(cart);

        var cartDto = cartMapper.toCartDto(cart);
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }
}
