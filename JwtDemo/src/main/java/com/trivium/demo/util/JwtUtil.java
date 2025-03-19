package com.trivium.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Create a secret key for signing and validating the token (must be at least 512 bits for HS512)
	private final Key key = Keys.hmacShaKeyFor("a-string-secret-at-least-256-bits-long".getBytes()); // 38 bytes
    // Generate a token for the provided username
    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date()) // Token issue time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5 minutes
                .signWith(key) // Sign with the secret key
                .compact(); // Return the compact JWT token
    }

    // Extract all claims from the token
    public Claims extractAllClaims(String token) {
        System.out.println("extractAllClaims..."+ Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // Extract the username from the token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject(); // The subject is the username
    }

    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date()); // Check if the token's expiration date is before the current date
    }

    // Validate the token by checking if it is expired and if the username matches
    public boolean validateToken(String token, String username) {
        // Here we check if the username matches and if the token is not expired
        System.out.println("Extracted Username: " + extractUsername(token)); // Optional: for debugging
        System.out.println("Provided Username: " + username); // Optional: for debugging
        System.out.println("Is token expired? " + !isTokenExpired(token)); // Optional: for debugging
        return (extractUsername(token).equals(username) && !isTokenExpired(token)); // Token is valid if username matches and token is not expired
    }
}
