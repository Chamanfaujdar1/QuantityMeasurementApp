package com.chaman.quantitymeasurement.controller;

import com.chaman.quantitymeasurement.model.User;
import com.chaman.quantitymeasurement.repository.UserRepository;
import com.chaman.quantitymeasurement.security.JwtUtil;
import com.chaman.quantitymeasurement.service.RefreshTokenService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/oauth")
public class OAuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @GetMapping("/oauth-success")
    public void oauthSuccess(
            @AuthenticationPrincipal OAuth2User user,
            HttpServletResponse response) throws IOException {

        String email = user.getAttribute("email");

        if (userRepo.findByUsername(email).isEmpty()) {
            User newUser = new User();
            newUser.setUsername(email);
            newUser.setPassword("OAUTH_USER");
            newUser.setRole("ROLE_USER");
            newUser.setProvider("GOOGLE");
            userRepo.save(newUser);
        }

        String accessToken = jwtUtil.generateToken(email);
        var refreshToken = refreshTokenService.createRefreshToken(email);

        String redirectUrl = String.format(
                "http://localhost:4200/auth/callback?accessToken=%s&refreshToken=%s",
                accessToken,
                refreshToken.getToken()
        );

        response.sendRedirect(redirectUrl);
    }
}
