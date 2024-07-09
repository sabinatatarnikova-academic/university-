package com.foxminded.university.service.user;

import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.dtos.response.users.UserResponse;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.repository.UserRepository;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.users.StudentMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import com.foxminded.university.utils.mappers.users.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StudyClassRepository studyClassRepository;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final UserMapper userMapper;
    private final CourseMapper courseMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserResponse userResponse) {
        userResponse.setPassword(passwordEncoder.encode(userResponse.getPassword()));
        if (userResponse.getUserType().equals("STUDENT")) {
            Student student = studentMapper.toEntity(userResponse);
            userRepository.save(student);
        } else {
            Teacher teacher = teacherMapper.toEntity(userResponse);
            userRepository.save(teacher);
        }
        log.debug("Saved user: type - {}", userResponse.getUserType());
    }

    @Override
    public User findUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            log.error("User with id {} not found", userId);
            throw new NoSuchElementException();
        }
        log.info("Founded the user with id {}", userId);
        return user.get();
    }

    @Override
    public UserResponse findUserDTOById(String userId) {
        User user = findUserById(userId);
        UserResponse dto = userMapper.toDto(user);
        log.info("User entity converted to dto");
        return dto;
    }

    @Override
    public User findUserByUsername(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        if (!user.isPresent()) {
            log.error("User with username {} not found", userName);
            throw new NoSuchElementException();
        }
        log.info("Founded the user with username {}", userName);
        return user.get();
    }

    @Override
    @Transactional
    public void updateStudent(UserFormRequest userFormRequest) {
        userFormRequest.setPassword(passwordEncoder.encode(userFormRequest.getPassword()));
        Student student = studentMapper.toEntity(userFormRequest);
        userRepository.save(student);
        log.info("Updated student with id - {}", student.getId());
    }

    @Override
    @Transactional
    public void updateTeacher(UserFormRequest userFormRequest) {
        userFormRequest.setPassword(passwordEncoder.encode(userFormRequest.getPassword()));
        Teacher teacher = teacherMapper.toEntity(userFormRequest);
        teacher.setStudyClasses(
                userFormRequest.getStudyClasses().stream()
                        .map(studyClassId -> assignTeacherToClass(teacher.getId(), studyClassId))
                        .collect(Collectors.toList())
        );
        userRepository.save(teacher);
        log.info("Updated teacher info: id - {}", teacher.getId());
    }

    private StudyClass assignTeacherToClass(String teacherId, String classId) {
        Optional<StudyClass> studyClassOptional = studyClassRepository.findById(classId);
        User teacher = findUserById(teacherId);
        if (!studyClassOptional.isPresent()) {
            log.error("StudyClass with id {} not found", classId);
            throw new NoSuchElementException();
        }
        StudyClass studyClass = studyClassOptional.get();
        studyClass.setTeacher((Teacher) teacher);
        studyClassRepository.save(studyClass);
        log.debug("Teacher was assigned to class");
        return studyClass;
    }

    @Override
    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
        log.info("Deleted user with id - {}", userId);
    }

    @Override
    public Page<UserResponse> findAllUsersWithPagination(RequestPage requestPage) {
        int pageNumber = requestPage.getPageNumber();
        int pageSize = requestPage.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> usersPage = userRepository.findAll(pageable);
        List<UserResponse> userResponses = usersPage.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        Page<UserResponse> pageResult = new PageImpl<>(userResponses, pageable, usersPage.getTotalElements());
        log.info("Found {} users", pageResult.getTotalPages());
        return pageResult;
    }

    @Override
    public Page<StudentResponse> findAllStudentsWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Student> students = userRepository.findAllStudents(pageable);
        log.info("Found all students");
        List<StudentResponse> studentResponses = students.stream().map(studentMapper::toDto).collect(Collectors.toList());
        return new PageImpl<>(studentResponses, pageable, students.getTotalElements());
    }

    @Override
    public Page<TeacherResponse> findAllTeachersWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Teacher> teachers = userRepository.findAllTeachers();
        log.info("Found all teachers");
        List<TeacherResponse> teacherResponses = teachers.stream().map(teacherMapper::toDto).collect(Collectors.toList());
        return new PageImpl<>(teacherResponses, pageable, teacherResponses.size());
    }

    @Override
    public List<TeacherResponse> findAllTeachers() {
        List<Teacher> teachers = userRepository.findAllTeachers();
        List<TeacherResponse> teacherResponses = teachers.stream().map(teacherMapper::toDto).collect(Collectors.toList());
        log.info("Found all teachers");
        return teacherResponses;
    }

    @Override
    @Transactional
    public void updateUser(UserFormRequest userFormRequest) {
        if (userFormRequest.getUserType().equalsIgnoreCase("STUDENT")) {
            updateStudent(userFormRequest);
            log.info("Student with id {} was updated.", userFormRequest.getId());
        } else if (userFormRequest.getUserType().equalsIgnoreCase("TEACHER")) {
            updateTeacher(userFormRequest);
            log.info("Teacher with id {} was updated.", userFormRequest.getId());
        }
    }

    @Override
    @Transactional
    public Page<CourseDTO> showCoursesThatAssignedToStudent(RequestPage pageRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Student student = (Student) findUserByUsername(username);
        List<CourseDTO> courses = student.getGroup()
                .getStudyClasses()
                .stream()
                .map(studyClass -> {
                    return courseMapper.toDto(studyClass.getCourse());
                }).collect(Collectors.toList());
        int classesCount = courses.size();

        log.info("Count of courses that assigned to student - {} is {}", student.getId(), classesCount);
        return new PageImpl<>(courses, pageable, classesCount);
    }

}
