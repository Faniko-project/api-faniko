package com.faniko.api_faniko.repositories;

import com.faniko.api_faniko.models.JwtRefreshToken;
import com.faniko.api_faniko.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JwtRefreshTokenRepository extends MongoRepository<JwtRefreshToken, String> {
    Optional<JwtRefreshToken> findFirstByTokenAndIsBlockedFalseAndDeletedAtIsNull(String token);
    Optional<JwtRefreshToken> findFirstByUserAndIsBlockedFalseAndDeletedAtIsNull(User user);
}
