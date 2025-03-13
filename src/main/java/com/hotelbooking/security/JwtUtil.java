package com.hotelbooking.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKeyString;

    private static final long JWT_EXPIRATION = 36000000; // 10 hours

    private SecretKey getSecretKey() {
        if (secretKeyString != null && !secretKeyString.isEmpty()) {
            return new javax.crypto.spec.SecretKeySpec(secretKeyString.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        } else {
            return Keys.secretKeyFor(SignatureAlgorithm.HS512); // This ensures the key is at least 512 bits
        }
    }

    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);  // Add role to the JWT payload

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)  // Use email as the subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // 1 hour expiration
                .signWith(getSecretKey())  // Use secure key for signing
                .compact();
    }

    // Method to validate JWT token
    public boolean validateToken(String token, String email) {
        String extractedEmail = extractEmail(token); // Extract the email from the token
        return (email.equals(extractedEmail) && !isTokenExpired(token));
    }

    // Extract the email from the token
    public String extractEmail(String token) {
        return parseClaims(token).getSubject(); // Get the subject which is the email
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        return parseClaims(token).getExpiration();
    }

    // Parse claims from the token
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
