package com.foxminded.university.service.user;

import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.dtos.users.UserFormDTO;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.repository.UserRepository;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.users.StudentMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import com.foxminded.university.utils.mappers.users.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userDTO.getUserType().equals("STUDENT")) {
            Student student = studentMapper.toEntity(userDTO);
            userRepository.save(student);
        } else {
            Teacher teacher = teacherMapper.toEntity(userDTO);
            userRepository.save(teacher);
        }
        log.debug("Saved user: firstName - {}, lastName - {}, type - {}", userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUserType());
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
    @Transactional
    public void updateStudent(UserFormDTO userFormDTO) {
        userFormDTO.setPassword(passwordEncoder.encode(userFormDTO.getPassword()));
        Student student = studentMapper.toEntity(userFormDTO);
        userRepository.save(student);
        log.info("Updated student info: firstName - {}, lastName - {}, group - {}", student.getFirstName(), student.getLastName(), student.getGroup());
    }

    @Override
    @Transactional
    public void updateTeacher(UserFormDTO userFormDTO) {
        userFormDTO.setPassword(passwordEncoder.encode(userFormDTO.getPassword()));
        Teacher teacher = teacherMapper.toEntity(userFormDTO);
        teacher.setStudyClasses(
                userFormDTO.getStudyClasses().stream()
                        .map(studyClassId -> assignTeacherToClass(teacher.getId(), studyClassId))
                        .collect(Collectors.toList())
        );
        userRepository.save(teacher);
        log.info("Updated teacher info: firstName - {}, lastName - {}", teacher.getFirstName(), teacher.getLastName()/*, teacherDTO.getStudyClasses()*/);
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
        return studyClass;
    }

    @Override
    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
        log.info("Deleted user with id - {}", userId);
    }

    @Override
    public Page<UserDTO> findAllUsersWithPagination(RequestPage requestPage) {
        int pageNumber = requestPage.getPageNumber();
        int pageSize = requestPage.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream().map(userMapper::toDto).collect(Collectors.toList());
        Page<UserDTO> pageResult = new PageImpl<>(userDTOs, pageable, userDTOs.size());
        log.info("Found {} users", pageResult.getTotalPages());
        return pageResult;
    }

    @Override
    public Page<StudentDTO> findAllStudentsWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        List<Student> students = userRepository.findAllStudents();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        log.info("Found all students");
        List<StudentDTO> studentDTOs = students.stream().map(studentMapper::toDto).collect(Collectors.toList());
        return new PageImpl<>(studentDTOs, pageable, studentDTOs.size());
    }

    @Override
    public Page<TeacherDTO> findAllTeachersWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Teacher> teachers = userRepository.findAllTeachers();

        log.info("Found all teachers");
        List<TeacherDTO> teacherDTOs = teachers.stream().map(teacherMapper::toDto).collect(Collectors.toList());
        return new PageImpl<>(teacherDTOs, pageable, teacherDTOs.size());
    }

    @Override
    public Object getUser(User user) {
        return user.getUserType().equals("TEACHER") ? teacherMapper.toDto((Teacher) user) : studentMapper.toDto((Student) user);
    }

    @Override
    @Transactional
    public void updateUser(UserFormDTO userFormDTO) {
        if (userFormDTO.getUserType().equalsIgnoreCase("STUDENT")) {
            updateStudent(userFormDTO);
            log.info("Student was updated.");
        } else if (userFormDTO.getUserType().equalsIgnoreCase("TEACHER")) {
            updateTeacher(userFormDTO);
            log.info("Student was updated.");
        }
    }
}
