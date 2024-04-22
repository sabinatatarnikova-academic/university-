package com.foxminded.university.service.user;

import com.foxminded.university.model.Group;
import com.foxminded.university.model.classes.StudyClass;
import com.foxminded.university.model.users.Student;
import com.foxminded.university.model.users.Teacher;
import com.foxminded.university.model.users.User;
import com.foxminded.university.model.users.dtos.StudentDTO;
import com.foxminded.university.model.users.dtos.TeacherDTO;
import com.foxminded.university.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DefaultUserService implements UserService{

    private final UserRepository userRepository;

    @Override
    public void saveStudent(String firstName, String lastName, Group group) {
        log.info("Adding new student: firstName - {}, lastName - {}, group - {}", firstName, lastName, group);
        StudentDTO student =  StudentDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .group(group)
                .build();
        userRepository.save(convertStudentDtoToEntity(student));
        log.info("Saved student: firstName - {}, lastName - {}, group - {}", firstName, lastName, group);
    }

    @Override
    public void saveTeacher(String firstName, String lastName, List<StudyClass> classes) {
        log.info("Adding new teacher: firstName - {}, lastName - {}, classes - {}", firstName, lastName, classes);
        TeacherDTO teacher =  TeacherDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .studyClasses(classes)
                .build();
        userRepository.save(convertTeacherDtoToEntity(teacher));
        log.info("Saved teacher: firstName - {}, lastName - {}, classes - {}", firstName, lastName, classes);
    }

    @Override
    public User findUserById(String userId) {
        log.info("Searching for user with id {}", userId);
        User user = userRepository.findById(userId).get();
        log.info("Founded the user with id {}", userId);
        return user;
    }

    @Override
    public void updateStudent(String userId, String firstName, String lastName, Group group) {
        log.info("Updating student info: firstName - {}, lastName - {}, group - {}", firstName, lastName, group);
        StudentDTO student =  StudentDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .group(group)
                .build();
        userRepository.save(convertStudentDtoToEntity(student));
        log.info("Updated student info: firstName - {}, lastName - {}, group - {}", firstName, lastName, group);
    }

    @Override
    public void updateTeacher(String userId, String firstName, String lastName, List<StudyClass> classes) {
        log.info("Updating teacher info: firstName - {}, lastName - {}, classes - {}", firstName, lastName, classes);
        TeacherDTO teacher =  TeacherDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .studyClasses(classes)
                .build();
        userRepository.save(convertTeacherDtoToEntity(teacher));
        log.info("Updated teacher info: firstName - {}, lastName - {}, classes - {}", firstName, lastName, classes);
    }

    @Override
    public void deleteUserById(String userId) {
        log.info("Deleting user with id {}", userId);
        userRepository.deleteById(userId);
        log.info("Deleted user with id - {}", userId);
    }

    @Override
    public List<User> findAllUsersWithPagination(int pageNumber, int pageSize) {
        log.info("Searching for user with page size {} and pageSize {}", pageNumber, pageSize);
        Page<User> pageResult = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} users", pageResult.getTotalPages());
        return pageResult.toList();
    }

    private Student convertStudentDtoToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setGroup(studentDTO.getGroup());
        return student;
    }
    private Teacher convertTeacherDtoToEntity(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDTO.getId());
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setStudyClasses(teacherDTO.getStudyClasses());
        return teacher;
    }
}
