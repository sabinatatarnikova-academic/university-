package com.foxminded.university.service.user;

import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.repository.UserRepository;
import com.foxminded.university.utils.UserCredentialGenerator;
import com.foxminded.university.utils.mappers.GroupMapper;
import com.foxminded.university.utils.mappers.users.StudentMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final StudyClassRepository studyClassRepository;
    private final UserCredentialGenerator utils;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final GroupMapper groupMapper;

    @Override
    @Transactional
    public void saveTeacher(TeacherDTO teacher) {
        teacher.setUsername(UserCredentialGenerator.generateUsername(teacher.getFirstName(), teacher.getLastName()));
        String rawPassword = UserCredentialGenerator.generatePassword();
        teacher.setRepeatedPassword(rawPassword);
        teacher.setPassword(utils.encodePassword(rawPassword));
        log.debug("Adding new teacher: firstName - {}, lastName - {}", teacher.getFirstName(), teacher.getLastName());
        userRepository.save(teacherMapper.toEntity(teacher));
        log.info("Saved teacher: firstName - {}, lastName - {}", teacher.getFirstName(), teacher.getLastName());
    }

    @Override
    public void saveStudent(StudentDTO student) {
        student.setUsername(UserCredentialGenerator.generateUsername(student.getFirstName(), student.getLastName()));
        String rawPassword = UserCredentialGenerator.generatePassword();
        student.setRepeatedPassword(rawPassword);
        student.setPassword(utils.encodePassword(rawPassword));
        log.debug("Adding new student: firstName - {}, lastName - {}, username - {},rawPassword - {}, password - {}", student.getFirstName(), student.getLastName(), student.getUsername(), student.getRepeatedPassword(), student.getPassword());
        userRepository.save(studentMapper.toEntity(student));
        log.info("Saved student: firstName - {}, lastName - {}, username - {},rawPassword - {}, password - {}", student.getFirstName(), student.getLastName(), student.getUsername(), student.getRepeatedPassword(), student.getPassword());
    }

    @Override
    public User findUserById(String userId) {
        log.debug("Searching for user with id {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            log.error("User with id {} not found", userId);
            throw new NoSuchElementException();
        }
        log.info("Founded the user with id {}", userId);
        return user.get();
    }

    @Override
    public void updateStudent(StudentDTO studentDTO) {
        log.debug("Updating student info: firstName - {}, lastName - {}, group - {}", studentDTO.getFirstName(), studentDTO.getLastName(), studentDTO.getGroup());
        studentDTO.setRepeatedPassword(studentDTO.getPassword());
        studentDTO.setPassword(utils.encodePassword(studentDTO.getRepeatedPassword()));
        Student student = studentMapper.toEntity(studentDTO);
        userRepository.save(student);
        log.info("Updated student info: firstName - {}, lastName - {}, group - {}", student.getFirstName(), student.getLastName(), student.getGroup());
    }

    @Override
    @Transactional
    public void updateTeacher(TeacherDTO teacherDTO) {
        teacherDTO.setRepeatedPassword(teacherDTO.getRepeatedPassword());
        teacherDTO.setPassword(utils.encodePassword(teacherDTO.getRepeatedPassword()));
        log.debug("Updating teacher info: firstName - {}, lastName - {}, classes - {}", teacherDTO.getFirstName(), teacherDTO.getLastName(), teacherDTO.getStudyClasses());
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        teacher.setStudyClasses(
                teacherDTO.getStudyClasses().stream()
                        .map(studyClassDTO -> assignTeacherToClass(teacher.getId(), studyClassDTO.getId()))
                        .collect(Collectors.toList())
        );
        userRepository.save(teacher);
        log.info("Updated teacher info: firstName - {}, lastName - {}, classes - {}", teacher.getFirstName(), teacher.getLastName(), teacherDTO.getStudyClasses());
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
        log.debug("Deleting user with id {}", userId);
        userRepository.deleteById(userId);
        log.info("Deleted user with id - {}", userId);
    }

    @Override
    public Page<User> findAllUsersWithPagination(int pageNumber, int pageSize) {
        log.debug("Searching for users with page size {} and pageSize {}", pageNumber, pageSize);
        Page<User> pageResult = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} users", pageResult.getTotalPages());
        return pageResult;
    }

    @Override
    public Page<Student> findAllStudentsWithPagination(int pageNumber, int pageSize) {
        log.debug("Searching for all students");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Student> allStudents = userRepository.findAllStudents(pageable);
        log.info("Found all students");
        return allStudents;
    }

    @Override
    public Page<Teacher> findAllTeachersWithPagination(int pageNumber, int pageSize) {
        log.debug("Searching for all teachers");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Teacher> allTeachers = userRepository.findAllTeachers(pageable);
        log.info("Found all teachers");
        return allTeachers;
    }
}
