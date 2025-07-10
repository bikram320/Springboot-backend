package com.example.spring_api_starter.exceptions;

import com.example.spring_api_starter.dtos.UserDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class DuplicateDataException extends RuntimeException {
    public DuplicateDataException(@NotBlank(message = "email should be provided") @Email(message = " email must be valid") String s) {
        super(s);
    }
}
