package com.foxminded.university.config;

import com.foxminded.university.restcontroller.AdminRestController;
import com.foxminded.university.service.classes.GlobalStudyClassesService;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.user.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class AdminRestControllerConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AdminRestController adminController(UserService userService, GroupService groupService, StudyClassService studyClassService, CourseService courseService, GlobalStudyClassesService globalStudyClassesService, ScheduleService scheduleService) {
        return new AdminRestController(userService, groupService, studyClassService, courseService, globalStudyClassesService, scheduleService);
    }
}
