package com.chaman.quantitymeasurement.controller;

import com.chaman.quantitymeasurement.dto.*;
import com.chaman.quantitymeasurement.model.RefreshToken;
import com.chaman.quantitymeasurement.model.User;
import com.chaman.quantitymeasurement.repository.RefreshTokenRepository;
import com.chaman.quantitymeasurement.repository.UserRepository;
import com.chaman.quantitymeasurement.security.JwtUtil;
import com.chaman.quantitymeasurement.service.RefreshTokenService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthRequest request) {

        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        user.setProvider("LOCAL");

        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String accessToken = jwtUtil.generateToken(request.getUsername());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(request.getUsername());

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody String requestToken) {

        RefreshToken token = refreshTokenRepository.findByToken(requestToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        String accessToken =
                jwtUtil.generateToken(token.getUser().getUsername());

        return ResponseEntity.ok(new AuthResponse(accessToken, requestToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header");
        }

        String token = header.substring(7);

        String username;
        try {
            username = jwtUtil.extractUsername(token);
        } catch (ExpiredJwtException e) {
            username = e.getClaims().getSubject();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        refreshTokenService.logoutByUsername(username);

        return ResponseEntity.ok("Logged out successfully");
    }
}
