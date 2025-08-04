package com.example.spring_api_starter.services;

import com.example.spring_api_starter.config.JwtConfig;
import com.example.spring_api_starter.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

    public String generateAccessToken(User user) {

        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }
    public String generateRefreshToken(User user) {
        return generateToken(user,jwtConfig.getRefreshTokenExpiration());
    }

    private String generateToken(User user , long tokenExpiration) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            var claims = getClaims(token);
            return claims.getExpiration().after(new Date());

        }catch(JwtException e){
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getUserIdByToken(String token) {
        try {
            return Long.valueOf(getClaims(token).getSubject());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid token subject: not a numeric user ID", e);
        }
    }
}
