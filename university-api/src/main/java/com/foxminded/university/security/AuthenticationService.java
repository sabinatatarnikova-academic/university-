package com.foxminded.university.security;

import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.model.entity.users.UserToken;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.service.user.UserTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private UserTokenService tokenService;
    private UserService userService;

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        UserToken userToken = tokenService.getUserTokenIfValid(token);
        if (userToken == null) {
            throw new RuntimeException();
        }
        String userId = userToken.getUserId();
        User user = userService.findUserById(userId);
        return new BasePlatformAuthentication(new UserPrincipal(userId, user.getUsername()), true);
    }
}
