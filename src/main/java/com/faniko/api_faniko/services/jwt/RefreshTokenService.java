package com.faniko.api_faniko.services.jwt;

import com.faniko.api_faniko.exceptions.NotFoundException;
import com.faniko.api_faniko.models.JwtRefreshToken;
import com.faniko.api_faniko.models.User;
import com.faniko.api_faniko.repositories.JwtRefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private JwtRefreshTokenRepository jwtRefreshTokenRepository;

    /**
     * Generate a refresh token for the user
     * @param user User {@link User}
     * @return Refresh token {@link String}
     */
    public String generateRefreshToken(User user) {
        JwtRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findFirstByUserAndIsBlockedFalseAndDeletedAtIsNull(user)
                .orElseGet(() -> {
                    JwtRefreshToken newJwtRefreshToken = JwtRefreshToken.builder()
                            .token(generateToken())
                            .isBlocked(Boolean.FALSE)
                            .user(user)
                            .build();
                    return jwtRefreshTokenRepository.save(newJwtRefreshToken);
                });

        return jwtRefreshToken.getToken();
    }

    /**
     * Get user from refresh token and delete the refresh token
     * @param token Refresh token {@link String}
     * @return User Linked to the refresh token {@link User}
     */
    public User getUserFromRefreshToken(String token) {
        JwtRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findFirstByTokenAndIsBlockedFalseAndDeletedAtIsNull(token)
                .orElseThrow(() -> new NotFoundException("Refresh token not found"));

        User user = jwtRefreshToken.getUser();

        jwtRefreshTokenRepository.delete(jwtRefreshToken);

        return user;
    }

    /**
     * Generate a refresh token
     * @return Refresh token {@link String}
     */
    private String generateToken(){
        String uuid = UUID.randomUUID().toString();
        return String.format("REFRESH%s%s", System.currentTimeMillis(), uuid.replaceAll("-", ""));
    }
}
