package com.chaman.quantitymeasurement.controller;

import com.chaman.quantitymeasurement.dto.UserProfileResponse;
import com.chaman.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.chaman.quantitymeasurement.model.User;
import com.chaman.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.chaman.quantitymeasurement.repository.UserRepository;
import com.chaman.quantitymeasurement.service.RefreshTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private QuantityMeasurementRepository quantityRepo;

    // 1. Get all users
    @GetMapping("/users")
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {

        List<UserProfileResponse> users = userRepo.findAll()
                .stream()
                .map(u -> new UserProfileResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getRole(),
                        u.getProvider()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    // 2. Promote user to ADMIN
    @PutMapping("/users/{username}/promote")
    public ResponseEntity<String> promoteToAdmin(@PathVariable String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        if (user.getRole().equals("ROLE_ADMIN")) {
            return ResponseEntity.badRequest().body("User is already an ADMIN");
        }

        user.setRole("ROLE_ADMIN");
        userRepo.save(user);

        return ResponseEntity.ok("User '" + username + "' promoted to ADMIN successfully");
    }

    // 3. Delete a user
    @DeleteMapping("/users/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // Delete refresh token first (FK constraint)
        refreshTokenService.logoutByUsername(username);

        userRepo.delete(user);

        return ResponseEntity.ok("User '" + username + "' deleted successfully");
    }

    // 4. Get all operation history across all users (paginated)
    @GetMapping("/history")
    public ResponseEntity<Page<QuantityMeasurementEntity>> getAllHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        return ResponseEntity.ok(quantityRepo.findAll(pageable));
    }
}
