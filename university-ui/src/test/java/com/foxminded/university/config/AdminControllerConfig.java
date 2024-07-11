package com.foxminded.university.config;

import com.foxminded.university.controller.AdminController;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.user.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@ComponentScan(basePackages = {
        "com.foxminded.university.utils"
})
public class AdminControllerConfig {

    @MockBean
    public PasswordEncoder passwordEncoder;

    @Bean
    public AdminController adminController(UserService userService, GroupService groupService, StudyClassService studyClassService, CourseService courseService) {
        return new AdminController(userService, groupService, studyClassService, courseService);
    }
}
