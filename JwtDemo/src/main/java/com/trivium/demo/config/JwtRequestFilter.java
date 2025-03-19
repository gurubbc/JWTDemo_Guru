package com.trivium.demo.config;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.trivium.demo.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	//OncePerRequestFilter is a class in the Spring Framework 
//	(specifically in Spring Security) that ensures a filter is 
//	applied only once per request in the filter chain. 
//	This helps avoid redundant filtering, which could occur if a 
//	filter is applied multiple times during the request lifecycle.
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    // This method is called only one time per request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	System.out.println("doFilterInternal");
        final String authorizationHeader = request.getHeader("authorization");
//        Enumeration<String> headerNames = request.getHeaderNames();
//
//        // Iterate through the header names and print them
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            System.out.println("Header Name: " + headerName);
//        }
        

        String username = null;
        String jwt = null;

        if (authorizationHeader!=null) {
        	System.out.println("Guru...authorizationHeader is not null");
        	System.out.println("Guru...authorizationHeader is "+authorizationHeader);
        } else {
        	System.out.println("Guru...authorizationHeader is null");
        	
        }
        
        
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // Remove "Bearer " (7 characters)
            System.out.println("Guru...The actual token " + jwt);

            try {
                // Extract the username from the JWT (ensure jwtUtil.extractUsername is working properly)
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                System.out.println("Guru...Failed to extract username from JWT");
                e.printStackTrace();
            }
        } else {
            System.out.println("Guru...authorization Header is null or doesn't start with 'Bearer'");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userDetails, userDetails.getAuthorities());
                jwtAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
            }
        }
        
        chain.doFilter(request, response); // since we don't have other filter in the chain,
//        the request will be forwarded to /admin or /user
    }	
}
