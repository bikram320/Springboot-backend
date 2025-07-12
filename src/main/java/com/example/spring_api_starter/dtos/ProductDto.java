package com.example.spring_api_starter.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.math.BigDecimal;





@Setter
@Getter
public class ProductDto {


    private Long id;

    @NotBlank(message = "Product name should Provided ")
    @Size(min = 3   , max = 30 , message = "Product name should be at least of 3 characters")
    private String name;

    @NotBlank(message = "Description should be provided")
    @Size(min = 10 , message = "Description should be at least of 10 characters")
    private String description;

    @NotBlank(message = "price should be provided")
    @Positive(message = "Price should be a positive number")
    private BigDecimal price;

    @NotBlank(message = "Category id should be provided")
    private long categoryId;

    public ProductDto(Long id, String name, String description, BigDecimal price, long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

}
