package com.foxminded.university.config;

import com.foxminded.university.controller.StudentController;
import com.foxminded.university.controller.TeacherController;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestControllerConfig {

    @Bean
    public TeacherController teacherController(UserService userService, PageUtils pageUtils){
        return new TeacherController(userService, pageUtils);
    }

    @Bean
    public StudentController studentController(UserService userService, PageUtils pageUtils){
        return new StudentController(userService, pageUtils);
    }

    @Bean
    public PageUtils pageUtils(){
        return new PageUtils();
    }
}
