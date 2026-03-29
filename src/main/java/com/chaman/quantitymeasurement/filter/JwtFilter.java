package com.chaman.quantitymeasurement.filter;

import com.chaman.quantitymeasurement.security.JwtUtil;
import com.chaman.quantitymeasurement.security.CustomUserDetailsService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println("Request Path: " + path);

        if (path.equals("/api/v1/auth/login") || path.equals("/api/v1/auth/register")) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        System.out.println("Header: " + header);

        if (header != null && header.startsWith("Bearer ")) {

            try {
                String token = header.substring(7);
                String username = jwtUtil.extractUsername(token);

                System.out.println("Extracted Username: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    var userDetails = userService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("Authentication SET");
                }

            } catch (Exception e) {
                System.out.println("JWT ERROR: " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}