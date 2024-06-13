package com.foxminded.university.config;

import com.foxminded.university.controller.StudentController;
import com.foxminded.university.controller.TeacherController;
import com.foxminded.university.service.user.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestControllerConfig {

    @Bean
    public TeacherController teacherController(UserService userService) {
        return new TeacherController(userService);
    }

    @Bean
    public StudentController studentController(UserService userService) {
        return new StudentController(userService);
    }
}
