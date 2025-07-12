package com.example.spring_api_starter.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @NotBlank(message = "name must be provided")
    @Size(min = 2,max = 50  , message ="name should be between 2 to 50 characters")
    private String name;

    @NotBlank(message ="Email should be provided")
    @Email(message = "Email should be Valid ")
    private String email;

}
