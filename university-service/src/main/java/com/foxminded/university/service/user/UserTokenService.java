package com.foxminded.university.service.user;

import com.foxminded.university.model.entity.users.UserToken;
import com.foxminded.university.repository.UserTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserTokenService {

    private final UserTokenRepository userTokenRepository;

    private final long TOKEN_EXPIRATION_MINUTES = 60;

    @Transactional
    public String generateToken(String userId) {
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        UserToken userToken = UserToken.builder()
                .token(token)
                .userId(userId)
                .createdAt(now)
                .expiresAt(now.plusMinutes(TOKEN_EXPIRATION_MINUTES))
                .build();

        userTokenRepository.deleteByUserId(userId);
        userTokenRepository.save(userToken);
        return token;
    }

    public UserToken getUserTokenIfValid(String token) {
        UserToken userToken = userTokenRepository.findByToken(token);
        if (userToken == null || userToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return null;
        }
        return userToken;
    }
}
