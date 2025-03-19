package com.trivium;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

public class ValidateJWT {

    public static void main(String[] args) {
        // Secret key must match the one used during signing and be at least 256 bits long
        String secretKey = "a-string-secret-at-least-256-bits-long";
        
        // Generate SecretKey from the string
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // JWT to validate
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTIzIiwiaWF0IjoxNzQyMzc4NzUwLCJleHAiOjE3NDIzNzg4MTAsInJvbGUxIjoic3dlZXBlciIsInJvbGUyIjoiZGJhIn0.w6YYifHPsVV3grxfjNZp_aGsdrV8Glm6a4Gr-D4gPQo";  // Replace with the JWT you want to verify

        try {
            // Parse the JWT and validate its signature
            Claims claims = Jwts.parser()
                .setSigningKey(key)  // Use SecretKey for validation
                .parseClaimsJws(jwt)
                .getBody();

            // Output the claims
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Role: " + claims.get("role1"));
            System.out.println("Role: " + claims.get("role2"));
            System.out.println("Expiration: " + claims.getExpiration());
        } catch (Exception e) {
            System.out.println("JWT validation failed: " + e.getMessage());
        }
    }
}
