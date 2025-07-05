package com.example.spring_api_starter.dtos;

import lombok.*;


import java.math.BigDecimal;





public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private long categoryId;

    public ProductDto(Long id, String name, String description, BigDecimal price, long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
