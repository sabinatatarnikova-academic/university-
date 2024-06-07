package com.foxminded.university.service.user;

import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.dtos.users.UserFormDTO;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.repository.UserRepository;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.UserCredentialUtils;
import com.foxminded.university.utils.mappers.users.StudentMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import com.foxminded.university.utils.mappers.users.UserMapper;
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
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final UserMapper userMapper;
    private final UserCredentialUtils userUtils;

    @Override
    public void saveUser(UserDTO userDTO) {
        userDTO.setPassword(userUtils.encodePassword(userDTO.getPassword()));
        log.debug("Adding new user: firstName - {}, lastName - {}, type - {}", userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUserType());
        userRepository.save(userMapper.toEntity(userDTO));
        log.debug("Saved user: firstName - {}, lastName - {}, type - {}", userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUserType());
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
    @Transactional
    public void updateStudent(UserFormDTO userFormDTO) {
        userFormDTO.setPassword(userUtils.encodePassword(userFormDTO.getPassword()));
        log.debug("Updating student info: firstName - {}, lastName - {}", userFormDTO.getFirstName(), userFormDTO.getLastName());
        Student student = studentMapper.toEntity(userFormDTO);
        userRepository.save(student);
        log.info("Updated student info: firstName - {}, lastName - {}, group - {}", student.getFirstName(), student.getLastName(), student.getGroup());
    }

    @Override
    @Transactional
    public void updateTeacher(UserFormDTO userFormDTO) {
        userFormDTO.setPassword(userUtils.encodePassword(userFormDTO.getPassword()));
        log.debug("Updating teacher info: firstName - {}, lastName - {}", userFormDTO.getFirstName(), userFormDTO.getLastName()/*, teacherDTO.getStudyClasses()*/);
        Teacher teacher = teacherMapper.toEntity(userFormDTO);
        teacher.setStudyClasses(
                userFormDTO.getStudyClassesIds().stream()
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
        log.debug("Deleting user with id {}", userId);
        userRepository.deleteById(userId);
        log.info("Deleted user with id - {}", userId);
    }

    @Override
    public Page<User> findAllUsersWithPagination(RequestPage requestPage) {
        int pageNumber = requestPage.getPageNumber();
        int pageSize = requestPage.getPageSize();
        log.debug("Searching for users with page size {} and pageSize {}", pageNumber, pageSize);
        Page<User> pageResult = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} users", pageResult.getTotalPages());
        return pageResult;
    }

    @Override
    public Page<Student> findAllStudentsWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        log.debug("Searching for all students");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Student> allStudents = userRepository.findAllStudents(pageable);
        log.info("Found all students");
        return allStudents;
    }

    @Override
    public Page<Teacher> findAllTeachersWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        log.debug("Searching for all teachers");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Teacher> allTeachers = userRepository.findAllTeachers(pageable);
        log.info("Found all teachers");
        return allTeachers;
    }
}
