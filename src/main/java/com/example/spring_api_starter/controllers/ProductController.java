package com.example.spring_api_starter.controllers;


import com.example.spring_api_starter.dtos.ProductDto;
import com.example.spring_api_starter.entities.Product;
import com.example.spring_api_starter.mapper.ProductMapper;
import com.example.spring_api_starter.repositories.CategoryRepository;
import com.example.spring_api_starter.repositories.ProductRepository;
import com.example.spring_api_starter.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;



@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> fetchAllProducts() {
        var products = productService.fetchAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/categoryId")
    public ResponseEntity<?> fetchProductsByCategory(@RequestParam(name = "categoryId" , required = false) Byte categoryId) {
        var products = productService.fetchByCategoryId(categoryId);
        return new ResponseEntity<>(products,HttpStatus.OK);

    }
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder) {

        var product = productService.createProduct(productDto);

        URI location = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(location).body(product);

    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable long id,
            @RequestBody ProductDto productDto){
        var product = productService.updateProduct(productDto,id);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(
            @PathVariable long id
    ){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
