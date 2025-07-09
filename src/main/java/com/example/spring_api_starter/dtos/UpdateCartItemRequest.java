package com.example.spring_api_starter.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {

    @NotNull(message = "quantity cannot be null")
    @Min(value = 1 , message = "quantity must be at least 1")
    @Max(value = 1000 , message = "quantity can be up to 1000")
    private Integer quantity;
}
