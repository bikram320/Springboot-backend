package com.example.spring_api_starter.mapper;

import com.example.spring_api_starter.dtos.ProductDto;
import com.example.spring_api_starter.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
//
//    @Mapping(source ="category.id",target = "categoryId")
//    ProductDto toProductDto(Product product);
//
//    Product toProduct(ProductDto productDto);
//
//    @Mapping(target = "id", ignore = true)
//    void updateProduct(ProductDto productDto, @MappingTarget Product product);

}
