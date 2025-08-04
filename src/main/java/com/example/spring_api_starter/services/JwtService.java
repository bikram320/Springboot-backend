package com.example.spring_api_starter.services;

import com.example.spring_api_starter.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.jwt.secret}")
    private String secret;

    public String generateAccessToken(User user) {
         final long tokenExpiration=300;
        return generateAccessToken(user,tokenExpiration);
    }
    public String generateRefreshToken(User user) {
        final long tokenExpiration=604800;
        return generateAccessToken(user,tokenExpiration);
    }

    private String generateAccessToken(User user , long tokenExpiration) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
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
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
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
