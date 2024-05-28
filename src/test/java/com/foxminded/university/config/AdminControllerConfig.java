/*
package com.foxminded.university.config;

import com.foxminded.university.controller.AdminController;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AdminControllerConfig {

    @Bean
    public AdminController adminController(UserService userService, GroupService groupService, StudyClassService studyClassService, PageUtils pageUtils){
        return new AdminController(userService, groupService, studyClassService, pageUtils);
    }

    @Bean
    public PageUtils pageUtils(){
        return new PageUtils();
    }
}
*/
