package com.foxminded.university.service.user;

import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.repository.UserRepository;
import com.foxminded.university.utils.mappers.users.StudentMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import com.foxminded.university.utils.mappers.users.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImlMockTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudyClassRepository studyClassRepository;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testUpdateUserWhenTypeIsStudent() {
        UserFormRequest student = UserFormRequest.builder()
                .firstName("Police")
                .lastName("Smith")
                .userType("STUDENT")
                .build();

        Student studentToReturn = Student.builder()
                .firstName("Police")
                .lastName("Smith")
                .group(Group.builder().name("group").build())
                .build();

        when(studentMapper.toEntity(any(UserFormRequest.class))).thenReturn(studentToReturn);
        userService.updateUser(student);

        verify(userService, times(1)).updateStudent(student);
    }

    @Test
    void testUpdateUserWhenTypeIsTeacher() {
        UserFormRequest teacher = UserFormRequest.builder()
                .firstName("Police")
                .lastName("Smith")
                .userType("TEACHER")
                .studyClassesIds(new ArrayList<>())
                .build();

        Teacher teacherToReturn = Teacher.builder()
                .firstName("Police")
                .lastName("Smith")
                .studyClasses(new ArrayList<>())
                .build();

        when(teacherMapper.toEntity(any(UserFormRequest.class))).thenReturn(teacherToReturn);
        userService.updateUser(teacher);

        verify(userService, times(1)).updateTeacher(teacher);
    }
}
