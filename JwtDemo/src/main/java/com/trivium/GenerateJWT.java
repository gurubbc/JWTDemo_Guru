package com.trivium;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class GenerateJWT {

    public static void main(String[] args) {
        // Generate a secure key for HS256
    	//Hash-based Message Authentication Code using the SHA
        SecretKey secretKey = Keys.hmacShaKeyFor("a-string-secret-at-least-256-bits-long".getBytes());  // Create SecretKey from the string
        
        
        String jwt = Jwts.builder()
            .setSubject("user123")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 60000*1)) // 5 minutes expiration
            .claim("role1", "sweeper")
            .claim("role2","dba")
            .signWith(secretKey, SignatureAlgorithm.HS256) // Use the correct signing algorithm
            .compact();
        
        

        System.out.println("Generated JWT: " + jwt);
    }
}
