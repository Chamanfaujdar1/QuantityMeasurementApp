package com.chaman.quantitymeasurement.filter;

import com.chaman.quantitymeasurement.security.JwtUtil;
import com.chaman.quantitymeasurement.security.CustomUserDetailsService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.contains("/api/v1/auth")
                || path.contains("/oauth2")
                || path.contains("/login")
                || path.contains("/api/v1/oauth")) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        log.debug("Incoming request: {} {}", request.getMethod(), path);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);
            String username = null;

            try {
                username = jwtUtil.extractUsername(token);
            } catch (Exception e) {
                log.warn("JWT validation failed for path {}: {}", path, e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid or Expired Token");
                return;
            }

            log.debug("Authenticated user: {}", username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                var userDetails = userService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } else {
            log.warn("Missing Authorization header for path: {}", path);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Missing Token");
            return;
        }

        chain.doFilter(request, response);
    }
}
