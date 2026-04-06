package com.chaman.quantitymeasurement.service;

import com.chaman.quantitymeasurement.model.RefreshToken;
import com.chaman.quantitymeasurement.model.User;
import com.chaman.quantitymeasurement.repository.RefreshTokenRepository;
import com.chaman.quantitymeasurement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional  // Apply to ALL methods in this service
public class RefreshTokenService {

    private final long refreshTokenDurationMs = 7 * 24 * 60 * 60 * 1000;

    @Autowired
    private RefreshTokenRepository repo;

    @Autowired
    private UserRepository userRepo;

    public RefreshToken createRefreshToken(String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // Delete old refresh token if exists
        repo.deleteByUserId(user.getId());
        repo.flush(); // ensure delete is committed before insert

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));

        return repo.save(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            repo.delete(token);
            throw new RuntimeException("Refresh token expired");
        }

        return token;
    }

    public void logoutByUsername(String username) {

        // Gracefully handles both logout and delete-user flows
        userRepo.findByUsername(username).ifPresent(user ->
                repo.deleteByUserId(user.getId())
        );
    }
}
