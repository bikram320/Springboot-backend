package com.example.spring_api_starter.dtos;

import lombok.Data;

@Data
public class ChangeUserPasswordRequest {
    private String oldPassword;
    private String newPassword;
}
