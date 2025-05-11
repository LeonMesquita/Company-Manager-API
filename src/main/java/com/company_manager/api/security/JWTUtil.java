package com.company_manager.api.security;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username) {
        SecretKey key = getKeyBySecret();
        return Jwts.builder()
                .header().type("JWT").and()  // Set the header explicitly
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + this.expiration))
                .signWith(key)  // Automatically detects HMAC
                .compact();
    }


    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaims(token);
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            return username != null && expirationDate != null && new Date().before(expirationDate);
        } catch (Exception e) {
            return false;
        }
    }


    private SecretKey getKeyBySecret() {
        return Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }

    private Claims getClaims(String token) {
        SecretKey key = getKeyBySecret();

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (Objects.nonNull(claims))
            return claims.getSubject();

        return null;
    }


}
