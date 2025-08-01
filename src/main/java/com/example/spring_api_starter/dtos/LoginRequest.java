package com.example.spring_api_starter.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoginRequest {

    @NotBlank(message = "Email should be Provided")
    @Email
    private String email;

    @NotBlank(message = "Password should be Provided")
    private String password;
}
