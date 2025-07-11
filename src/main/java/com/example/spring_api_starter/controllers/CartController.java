package com.example.spring_api_starter.controllers;

import com.example.spring_api_starter.dtos.AddItemToCartRequest;
import com.example.spring_api_starter.dtos.CartDto;
import com.example.spring_api_starter.dtos.UpdateCartItemRequest;
import com.example.spring_api_starter.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.UUID;

@RestController()
@AllArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;


    @PostMapping
    public ResponseEntity<?> createCart(UriComponentsBuilder uriBuilder) {
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<?> addToCart(
            @PathVariable UUID cartId,@RequestBody AddItemToCartRequest request) {

        var cartDto = cartService.addToCart(request, cartId);
        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
        var cartDto = cartService.getCart(cartId);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") long productId,
            @Valid @RequestBody UpdateCartItemRequest request
            ){

        var carDto = cartService.updateCart(cartId,request,productId);
        return ResponseEntity.ok(carDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteItem(
            @PathVariable UUID cartId,
            @PathVariable long productId
    ){
        cartService.deleteItem(cartId,productId);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(@PathVariable UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
