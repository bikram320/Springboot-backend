package com.example.spring_api_starter.dtos;

import lombok.*;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Getter

@Setter

public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private byte categoryId;
}
