package com.foxminded.university.config;

import com.foxminded.university.controller.StudentController;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.user.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {
        "com.foxminded.university.utils"
})
public class StudentControllerConfig {
    @Bean
    public StudentController studentController(UserService userService, ScheduleService scheduleService) {
        return new StudentController(userService, scheduleService);
    }
}
