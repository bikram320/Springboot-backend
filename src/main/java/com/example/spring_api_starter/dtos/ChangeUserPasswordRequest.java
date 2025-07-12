package com.example.spring_api_starter.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangeUserPasswordRequest {

    @NotBlank(message = "Old password should be provided")
    private String oldPassword;

    @NotBlank(message = "New password should be provided")
    @Size(min = 6 , max = 100 , message = "password should be at least  6 to 100 characters")
    private String newPassword;
}
