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
import com.foxminded.university.utils.UserCredentialGenerator;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.CourseMapperImpl;
import com.foxminded.university.utils.mappers.GroupMapper;
import com.foxminded.university.utils.mappers.GroupMapperImpl;
import com.foxminded.university.utils.mappers.LocationMapper;
import com.foxminded.university.utils.mappers.LocationMapperImpl;
import com.foxminded.university.utils.mappers.classes.OfflineClassMapper;
import com.foxminded.university.utils.mappers.classes.OfflineClassMapperImpl;
import com.foxminded.university.utils.mappers.classes.OnlineClassMapper;
import com.foxminded.university.utils.mappers.classes.OnlineClassMapperImpl;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import com.foxminded.university.utils.mappers.classes.StudyClassMapperImpl;
import com.foxminded.university.utils.mappers.users.StudentMapper;
import com.foxminded.university.utils.mappers.users.StudentMapperImpl;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapperImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
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
    public StudyClassService studyClassService(StudyClassRepository studyClassRepository, OnlineClassMapper onlineClassMapper, OfflineClassMapper offlineClassMapper, TeacherMapper teacherMapper) {
        return new DefaultStudyClassService(studyClassRepository, offlineClassMapper, onlineClassMapper, teacherMapper);
    }

    @Bean
    public UserService userService(UserRepository userRepository, StudyClassRepository studyClassRepository, UserCredentialGenerator userUtils, StudentMapper studentMapper, TeacherMapper teacherMapper, GroupMapper groupMapper) {
        return new DefaultUserService(userRepository, studyClassRepository, userUtils, studentMapper, teacherMapper, groupMapper);
    }

    @Bean
    public UserCredentialGenerator userUtils(PasswordEncoder passwordEncoder) {
        return new UserCredentialGenerator(passwordEncoder);
    }

    @Bean
    public StudentMapper studentMapper(){
        return new StudentMapperImpl();
    }

    @Bean
    public TeacherMapper teacherMapper(){
        return new TeacherMapperImpl();
    }

    @Bean
    public OnlineClassMapper onlineClassMapper(){
        return new OnlineClassMapperImpl();
    }

    @Bean
    public OfflineClassMapper offlineClassMapper(){
        return new OfflineClassMapperImpl();
    }

    @Bean
    public StudyClassMapper studyClassMapper(){
        return new StudyClassMapperImpl();
    }

    @Bean
    public CourseMapper courseMapper(){
        return new CourseMapperImpl();
    }

    @Bean
    public GroupMapper groupMapper(){
        return new GroupMapperImpl();
    }

    @Bean
    public LocationMapper locationMapper(){
        return new LocationMapperImpl();
    }
}
