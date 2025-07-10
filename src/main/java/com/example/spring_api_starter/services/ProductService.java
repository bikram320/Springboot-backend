package com.example.spring_api_starter.services;

import com.example.spring_api_starter.dtos.ProductDto;
import com.example.spring_api_starter.entities.Product;
import com.example.spring_api_starter.mapper.ProductMapper;
import com.example.spring_api_starter.repositories.CategoryRepository;
import com.example.spring_api_starter.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private  final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    
    public List<ProductDto> fetchAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductDto)
                .toList();
    }

    public List<ProductDto> fetchByCategoryId(Byte categoryId) {
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
