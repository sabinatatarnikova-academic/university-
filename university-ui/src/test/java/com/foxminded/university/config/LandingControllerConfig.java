package com.foxminded.university.config;

import com.foxminded.university.controller.LandingController;
import com.foxminded.university.service.course.CourseService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {
        "com.foxminded.university.utils"
})
public class LandingControllerConfig {

    @Bean
    public LandingController landingController(CourseService courseService) {
        return new LandingController(courseService);
    }
}
