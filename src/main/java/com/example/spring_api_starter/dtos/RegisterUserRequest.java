package com.example.spring_api_starter.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegisterUserRequest {
    private String name ;
    private String email ;
    private String password ;

}
