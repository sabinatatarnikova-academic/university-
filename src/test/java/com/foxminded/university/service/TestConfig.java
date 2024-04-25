package com.foxminded.university.service;

import com.foxminded.university.repository.CourseRepository;
import com.foxminded.university.repository.GroupRepository;
import com.foxminded.university.repository.LocationRepository;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.repository.UserRepository;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.course.DefaultCourseService;
import com.foxminded.university.service.group.DefaultGroupService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.location.DefaultLocationService;
import com.foxminded.university.service.location.LocationService;
import com.foxminded.university.service.studyClasses.DefaultStudyClassService;
import com.foxminded.university.service.studyClasses.StudyClassService;
import com.foxminded.university.service.user.DefaultUserService;
import com.foxminded.university.service.user.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public GroupService groupService(GroupRepository groupRepository){
        return new DefaultGroupService(groupRepository);
    }

    @Bean
    public CourseService courseService(CourseRepository courseRepository){
        return new DefaultCourseService(courseRepository);
    }

    @Bean
    public LocationService locationService(LocationRepository locationRepository){
        return new DefaultLocationService(locationRepository);
    }

    @Bean
    public StudyClassService studyClassService(StudyClassRepository studyClassRepository){
        return new DefaultStudyClassService(studyClassRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository){
        return new DefaultUserService(userRepository);
    }
}
