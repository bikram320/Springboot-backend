package com.example.spring_api_starter.controllers;

import com.example.spring_api_starter.config.JwtConfig;
import com.example.spring_api_starter.dtos.JwtResponse;
import com.example.spring_api_starter.dtos.LoginRequest;
import com.example.spring_api_starter.entities.User;
import com.example.spring_api_starter.mapper.UserMapper;
import com.example.spring_api_starter.repositories.UserRepository;
import com.example.spring_api_starter.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var accessToken =jwtService.generateAccessToken(user);
        var refreshToken =jwtService.generateRefreshToken(user);

        var cookie = new Cookie("Refresh", refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @PostMapping("/validate")
    public boolean validateToken(@RequestHeader("authorization") String authHeader) {
        System.out.println("Validate Called");
        var token = authHeader.replace("Bearer ", "");
        return jwtService.validateToken(token);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId =(Long) authentication.getPrincipal();

        var user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var userDto = userMapper.toUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentials(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
