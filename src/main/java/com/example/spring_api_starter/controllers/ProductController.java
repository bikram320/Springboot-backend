package com.example.spring_api_starter.controllers;


import com.example.spring_api_starter.dtos.ProductDto;
import com.example.spring_api_starter.entities.Product;
import com.example.spring_api_starter.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public Iterable<ProductDto> fetchAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product ->
                        new ProductDto(product.getId(), product.getName(), product.getDescription(),product.getPrice(),product.getCategory().getId()))
                .toList();
    }
    @GetMapping("/categoryId{categoryId}")
    public Iterable<ProductDto> fetchProductsByCategory(@RequestParam(name = "categoryId" , required = false) Byte categoryId) {

        List<Product> products;
        if (categoryId != null) {
            products=productRepository.findByCategoryId(categoryId);
        }else{
            products=productRepository.findAllWithCategory();
        }
        return products.stream().map(product ->new ProductDto(product.getId(), product.getName(), product.getDescription(),product.getPrice(),product.getCategory().getId()))
                .toList();

    }
}
