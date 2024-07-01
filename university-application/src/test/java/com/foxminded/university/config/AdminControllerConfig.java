package com.foxminded.university.config;

import com.foxminded.university.controller.AdminController;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.location.LocationService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import com.foxminded.university.utils.mappers.users.UserMapper;
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
    public AdminController adminController(UserService userService, GroupService groupService, StudyClassService studyClassService, UserMapper userMapper, StudyClassMapper studyClassMapper, CourseService courseService, CourseMapper courseMapper) {
        return new AdminController(userService, userMapper, groupService, studyClassService, studyClassMapper, courseService, courseMapper);
    }
}
