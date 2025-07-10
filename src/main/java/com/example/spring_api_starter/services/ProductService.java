package com.example.spring_api_starter.services;

import com.example.spring_api_starter.dtos.ProductDto;
import com.example.spring_api_starter.entities.Product;
import com.example.spring_api_starter.exceptions.ResourceNotFoundException;
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
            throw new ResourceNotFoundException("Category id not found"+categoryId);
        }
        return products.stream().map(productMapper::toProductDto)
                .toList();
    }
    public ProductDto createProduct(ProductDto productDto) {
        var category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        var product = productMapper.toProduct(productDto);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());
        return productMapper.toProductDto(product);
    }

    public ProductDto updateProduct(ProductDto productDto, long id) {
        var product = productRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productMapper.updateProduct(productDto,product);
        productRepository.save(product);
        return productMapper.toProductDto(product);
    }

    public void deleteProduct(long id) {
        var product = productRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

}
