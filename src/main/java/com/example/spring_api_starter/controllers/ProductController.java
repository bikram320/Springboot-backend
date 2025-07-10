package com.example.spring_api_starter.controllers;


import com.example.spring_api_starter.dtos.ProductDto;
import com.example.spring_api_starter.entities.Product;
import com.example.spring_api_starter.mapper.ProductMapper;
import com.example.spring_api_starter.repositories.CategoryRepository;
import com.example.spring_api_starter.repositories.ProductRepository;
import com.example.spring_api_starter.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    @GetMapping
    public Iterable<ProductDto> fetchAllProducts() {
        return productService.fetchAll();
    }
    @GetMapping("/categoryId")
    public Iterable<ProductDto> fetchProductsByCategory(@RequestParam(name = "categoryId" , required = false) Byte categoryId) {
        return productService.fetchByCategoryId(categoryId);

    }
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category == null) {
            return ResponseEntity.notFound().build();
        }

        var product = productMapper.toProduct(productDto);
        product.setCategory(category);
        productRepository.save(product);

        productDto.setId(product.getId());
        URI location = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(location).body(productMapper.toProductDto(product));

    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable long id,
            @RequestBody ProductDto productDto){
        var product = productRepository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        productMapper.updateProduct(productDto,product);
        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toProductDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(
            @PathVariable long id
    ){
        var product = productRepository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }
}
