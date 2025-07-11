package com.example.spring_api_starter.services;

import com.example.spring_api_starter.dtos.AddItemToCartRequest;
import com.example.spring_api_starter.dtos.CartDto;
import com.example.spring_api_starter.dtos.CartItemDto;
import com.example.spring_api_starter.dtos.UpdateCartItemRequest;
import com.example.spring_api_starter.entities.Cart;
import com.example.spring_api_starter.exceptions.ResourceNotFoundException;
import com.example.spring_api_starter.mapper.CartMapper;
import com.example.spring_api_starter.repositories.CartRepository;
import com.example.spring_api_starter.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);

        return cartMapper.toCartDto(cart);
    }

    public CartItemDto addToCart(AddItemToCartRequest request , UUID cartId){
        var cart = cartRepository.findById(cartId)
                .orElseThrow( ()-> new ResourceNotFoundException("Cart not found") );

        var product= productRepository.findById(request.getProductId())
                .orElseThrow( ()-> new ResourceNotFoundException("Product not found") );

        var cartItem =cart.addItem(product);
        cartRepository.save(cart);

        return cartMapper.toCartDto(cartItem);
    }

    public CartDto getCart(UUID cartId){
        var cart = cartRepository.getCartWithItems(cartId)
                .orElseThrow( ()-> new ResourceNotFoundException("Cart not found") );
        return cartMapper.toCartDto(cart);
    }

    public CartDto updateCart(UUID cartId, UpdateCartItemRequest request , long productId){
        var cart = cartRepository.getCartWithItems(cartId)
                .orElseThrow( ()-> new ResourceNotFoundException("Cart not found") );

        var cartItem =cart.getItem(productId);
        if (cartItem == null) {
            throw new ResourceNotFoundException("Cart item not found");
        }
        cartItem.setQuantity(request.getQuantity());
        cartRepository.save(cart);
        return cartMapper.toCartDto(cart);
    }

    public void deleteItem(UUID cartId, long productId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }
        var cartItem =cart.getItem(productId);
        if (cartItem == null) {
            throw new ResourceNotFoundException("Cart item not found");
        }
        cart.removeItem(productId);
        cartRepository.save(cart);
    }
    public void clearCart(UUID cartId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }
        cart.clear();
        cartRepository.save(cart);
    }
}
