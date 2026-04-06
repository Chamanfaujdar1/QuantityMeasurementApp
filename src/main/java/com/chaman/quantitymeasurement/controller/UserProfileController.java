package com.chaman.quantitymeasurement.controller;

import com.chaman.quantitymeasurement.dto.UserProfileResponse;
import com.chaman.quantitymeasurement.model.User;
import com.chaman.quantitymeasurement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserProfileController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileResponse response = new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getProvider()
        );

        return ResponseEntity.ok(response);
    }
}
