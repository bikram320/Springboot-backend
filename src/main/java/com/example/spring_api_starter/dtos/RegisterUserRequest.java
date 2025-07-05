package com.example.spring_api_starter.dtos;

import com.example.spring_api_starter.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegisterUserRequest {

    @NotBlank(message = "Name must be provided")
    @Size(min = 2, max = 255  , message = "name must be between 2 to 255 character")
    private String name ;

    @NotBlank(message = "email should be provided")
    @Email(message = " email must be valid")
    @Lowercase
    private String email ;

    @NotBlank
    @Size(min = 6 , max = 25 , message = "password must be between 6 to 25 characters")
    private String password ;

}
