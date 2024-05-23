package com.foxminded.university.service.user;

import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.repository.UserRepository;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.utils.ConverterDtoToEntity;
import com.foxminded.university.utils.UserCredentialGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final GroupService groupService;
    private final StudyClassRepository studyClassRepository;
    private final UserCredentialGenerator utils;
    private final ConverterDtoToEntity converter;

    @Override
    @Transactional
    public void saveTeacher(TeacherDTO teacher) {
        teacher.setUsername(UserCredentialGenerator.generateUsername(teacher.getFirstName(), teacher.getLastName()));
        String rawPassword = UserCredentialGenerator.generatePassword();
        teacher.setRawPassword(rawPassword);
        teacher.setPassword(utils.encodePassword(rawPassword));
        List<StudyClass> studyClasses = getStudyClasses(teacher);
        log.debug("Adding new teacher: firstName - {}, lastName - {}, classes - {}", teacher.getFirstName(), teacher.getLastName(), studyClasses);
        userRepository.save(converter.convertTeacherDtoToEntity(teacher, studyClasses));
        log.info("Saved teacher: firstName - {}, lastName - {}, classes - {}", teacher.getFirstName(), teacher.getLastName(), studyClasses);
    }

    @Override
    public void saveStudent(StudentDTO student) {
        student.setUsername(UserCredentialGenerator.generateUsername(student.getFirstName(), student.getLastName()));
        String rawPassword = UserCredentialGenerator.generatePassword();
        student.setRawPassword(rawPassword);
        student.setPassword(utils.encodePassword(rawPassword));
        Group group = groupService.findGroupById(student.getGroupId());
        log.debug("Adding new student: firstName - {}, lastName - {}, group - {}, username - {},rawPassword - {}, password - {}", student.getFirstName(), student.getLastName(), group, student.getUsername(), student.getRawPassword(), student.getPassword());
        userRepository.save(converter.convertStudentDtoToEntity(student, group));
        log.info("Saved student: firstName - {}, lastName - {}, group - {}, username - {},rawPassword - {}, password - {}", student.getFirstName(), student.getLastName(), group, student.getUsername(), student.getRawPassword(), student.getPassword());
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
    public void updateStudent(StudentDTO student) {
        log.debug("Updating student info: firstName - {}, lastName - {}, group - {}", student.getFirstName(), student.getLastName(), student.getGroupId());
        student.setRawPassword(student.getPassword());
        student.setPassword(utils.encodePassword(student.getPassword()));
        Group group = groupService.findGroupById(student.getGroupId());
        userRepository.save(converter.convertStudentDtoToEntity(student, group));
        log.info("Updated student info: firstName - {}, lastName - {}, group - {}", student.getFirstName(), student.getLastName(), student.getGroupId());
    }

    @Override
    @Transactional
    public void updateTeacher(TeacherDTO teacher) {
        teacher.setRawPassword(teacher.getRawPassword());
        teacher.setPassword(utils.encodePassword(teacher.getPassword()));
        List<StudyClass> studyClasses = getStudyClasses(teacher);
        log.debug("Updating teacher info: firstName - {}, lastName - {}, classes - {}", teacher.getFirstName(), teacher.getLastName(), studyClasses);
        userRepository.save(converter.convertTeacherDtoToEntity(teacher, studyClasses));
        log.info("Updated teacher info: firstName - {}, lastName - {}, classes - {}", teacher.getFirstName(), teacher.getLastName(), studyClasses);
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

    private List<StudyClass> getStudyClasses(TeacherDTO teacher) {
        Set<String> studyClassesIds = teacher.getStudyClassesIds();
        List<StudyClass> studyClasses = new ArrayList<>();
        if (!(teacher.getStudyClassesIds() == null)){
            studyClassesIds.forEach(string -> {
                Optional <StudyClass> studyClass = studyClassRepository.findById(string);
                if (studyClass.isPresent()){
                    studyClasses.add(studyClass.get());
                }
            });
        }
        return studyClasses;
    }
}
