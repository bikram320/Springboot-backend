package com.example.spring_api_starter.controllers;

import com.example.spring_api_starter.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @RequestMapping("/hello")
    public Message getMessage() {
        return new Message ("Hello World");
    }
}
