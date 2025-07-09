package com.example.spring_api_starter.controllers;

import com.example.spring_api_starter.dtos.AddItemToCartRequest;
import com.example.spring_api_starter.dtos.CartDto;
import com.example.spring_api_starter.entities.Cart;
import com.example.spring_api_starter.entities.CartItem;
import com.example.spring_api_starter.mapper.CartMapper;
import com.example.spring_api_starter.repositories.CartRepository;
import com.example.spring_api_starter.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController()
@AllArgsConstructor
@RequestMapping("/carts")
public class CartController {


    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;


    @PostMapping
    public ResponseEntity<?> createCart(UriComponentsBuilder uriBuilder) {
        var cart = new Cart();
        cartRepository.save(cart);

        var cartDto = cartMapper.toCartDto(cart);
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<?> addToCart(
            @PathVariable UUID cartId,@RequestBody AddItemToCartRequest request) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

       var product= productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        var cartItem =cart.getItems().stream()
                .filter(items -> items.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if(cartItem !=null){
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);

        }
        cartRepository.save(cart);

        var cartDto = cartMapper.toCartDto(cartItem);

        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        var cartDto = cartMapper.toCartDto(cart);
        return ResponseEntity.ok(cartDto);
    }
}
