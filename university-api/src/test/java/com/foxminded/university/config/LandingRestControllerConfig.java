package com.foxminded.university.config;

import com.foxminded.university.restcontroller.LandingRestController;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.service.user.UserTokenService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;

@TestConfiguration
@ComponentScan(basePackages = {
        "com.foxminded.university.utils"
})
public class LandingRestControllerConfig {

    @Bean
    public LandingRestController landingController(CourseService courseService, UserTokenService tokenService, UserService userService, AuthenticationManager authenticationManager) {
        return new LandingRestController(courseService, tokenService, userService, authenticationManager);
    }
}
