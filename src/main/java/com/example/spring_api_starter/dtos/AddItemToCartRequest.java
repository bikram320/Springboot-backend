package com.example.spring_api_starter.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartRequest {

    @NotNull(message = "ProductId is needed")
    private Long productId;
}
