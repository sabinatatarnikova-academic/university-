package com.foxminded.university.config;

import com.foxminded.university.controller.AdminController;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import com.foxminded.university.utils.mappers.classes.StudyClassMapperImpl;
import com.foxminded.university.utils.mappers.users.StudentMapper;
import com.foxminded.university.utils.mappers.users.StudentMapperImpl;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapperImpl;
import com.foxminded.university.utils.mappers.users.UserMapper;
import com.foxminded.university.utils.mappers.users.UserMapperImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AdminControllerConfig {

    @Bean
    public AdminController adminController(UserService userService, GroupService groupService, StudyClassService studyClassService, PageUtils pageUtils, StudyClassMapper studyClassMapper, TeacherMapper teacherMapper, StudentMapper studentMapper, UserMapper userMapper) {
        return new AdminController(userService, groupService, studyClassService, pageUtils, studyClassMapper, teacherMapper, studentMapper, userMapper);
    }

    @Bean
    public PageUtils pageUtils(){
        return new PageUtils();
    }

    @Bean
    public StudyClassMapper studyClassMapper() {
        return new StudyClassMapperImpl();
    }

    @Bean
    public TeacherMapper teacherMapper(){
        return new TeacherMapperImpl();
    }

    @Bean
    public StudentMapper studentMapper(){
        return new StudentMapperImpl();
    }

    @Bean
    public UserMapper userMapper(){
        return new UserMapperImpl();
    }
}
