package com.foxminded.university.config;

import com.foxminded.university.restcontroller.AdminRestController;
import com.foxminded.university.service.classes.GlobalStudyClassesService;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.user.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class AdminRestControllerConfig {

    @MockBean
    public PasswordEncoder passwordEncoder;

    @Bean
    public AdminRestController adminController(UserService userService, GroupService groupService, StudyClassService studyClassService, CourseService courseService, GlobalStudyClassesService globalStudyClassesService, ScheduleService scheduleService) {
        return new AdminRestController(userService, groupService, studyClassService, courseService, globalStudyClassesService, scheduleService);
    }
}