package com.example.spring_api_starter.controllers;


import com.example.spring_api_starter.dtos.ProductDto;
import com.example.spring_api_starter.entities.Product;
import com.example.spring_api_starter.mapper.ProductMapper;
import com.example.spring_api_starter.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public Iterable<ProductDto> fetchAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductDto)
                .toList();
    }
    @GetMapping("/categoryId/{categoryId}")
    public Iterable<ProductDto> fetchProductsByCategory(@RequestParam(name = "categoryId" , required = false) Byte categoryId) {

        List<Product> products;
        if (categoryId != null) {
            products=productRepository.findByCategoryId(categoryId);
        }else{
            products=productRepository.findAllWithCategory();
        }
        return products.stream().map(productMapper::toProductDto)
                .toList();

    }
}
