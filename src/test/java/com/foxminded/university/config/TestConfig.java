package com.foxminded.university.config;

import com.foxminded.university.repository.CourseRepository;
import com.foxminded.university.repository.GroupRepository;
import com.foxminded.university.repository.LocationRepository;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.repository.UserRepository;
import com.foxminded.university.service.classes.DefaultStudyClassService;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.course.DefaultCourseService;
import com.foxminded.university.service.group.DefaultGroupService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.location.DefaultLocationService;
import com.foxminded.university.service.location.LocationService;
import com.foxminded.university.service.user.DefaultUserService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.ConverterDtoToEntity;
import com.foxminded.university.utils.UserCredentialGenerator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public StudyClassService studyClassService(StudyClassRepository studyClassRepository, UserService userService, CourseService courseService, GroupService groupService, LocationService locationService, ConverterDtoToEntity converterDtoToEntity) {
        return new DefaultStudyClassService(studyClassRepository, userService, courseService, groupService, locationService, converterDtoToEntity);
    }

    @Bean
    public UserService userService(UserRepository userRepository, GroupService groupService, StudyClassRepository studyClassRepository, UserCredentialGenerator userUtils, ConverterDtoToEntity converter ) {
        return new DefaultUserService(userRepository, groupService, studyClassRepository, userUtils, converter);
    }

    @Bean
    public UserCredentialGenerator userUtils(PasswordEncoder passwordEncoder) {
        return new UserCredentialGenerator(passwordEncoder);
    }

    @Bean
    public ConverterDtoToEntity converter (){
        return new ConverterDtoToEntity();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

/*    @Bean
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
    }*/
}
