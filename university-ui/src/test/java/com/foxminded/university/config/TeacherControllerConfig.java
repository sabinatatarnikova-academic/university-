package com.foxminded.university.config;

import com.foxminded.university.controller.TeacherController;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.user.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {
        "com.foxminded.university.utils"
})
public class TeacherControllerConfig {

    @Bean
    public TeacherController teacherController(UserService userService, GroupService groupService, StudyClassService studyClassService, CourseService courseService, ScheduleService scheduleService) {
        return new TeacherController(userService, groupService, studyClassService, courseService, scheduleService);
    }


}
