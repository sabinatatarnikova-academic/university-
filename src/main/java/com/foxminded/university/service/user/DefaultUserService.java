package com.foxminded.university.service.user;

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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class DefaultUserService implements UserService{

    private final UserRepository userRepository;

    @Override
    public void saveStudent(StudentDTO studentDTO) {
        log.info("Adding new student: firstName - {}, lastName - {}, group - {}", studentDTO.getFirstName(),studentDTO.getLastName(), studentDTO.getGroup());
        userRepository.save(convertStudentDtoToEntity(studentDTO));
        log.info("Saved student: firstName - {}, lastName - {}, group - {}", studentDTO.getFirstName(),studentDTO.getLastName(), studentDTO.getGroup());
    }

    @Override
    public void saveTeacher(TeacherDTO teacher) {
        log.info("Adding new teacher: firstName - {}, lastName - {}, classes - {}", teacher.getFirstName(), teacher.getLastName(), teacher.getStudyClasses());
        userRepository.save(convertTeacherDtoToEntity(teacher));
        log.info("Saved teacher: firstName - {}, lastName - {}, classes - {}", teacher.getFirstName(), teacher.getLastName(), teacher.getStudyClasses());
    }

    @Override
    public User findUserById(String userId) {
        log.info("Searching for user with id {}", userId);
        User user = userRepository.findById(userId).get();
        log.info("Founded the user with id {}", userId);
        return user;
    }

    @Override
    public void updateStudent(StudentDTO student) {
        log.info("Updating student info: firstName - {}, lastName - {}, group - {}", student.getFirstName(), student.getLastName(), student.getGroup());
        userRepository.save(convertStudentDtoToEntity(student));
        log.info("Updated student info: firstName - {}, lastName - {}, group - {}", student.getFirstName(), student.getLastName(), student.getGroup());
    }

    @Override
    public void updateTeacher(TeacherDTO teacher) {
        log.info("Updating teacher info: firstName - {}, lastName - {}, classes - {}", teacher.getFirstName(), teacher.getLastName(), teacher.getStudyClasses());
        userRepository.save(convertTeacherDtoToEntity(teacher));
        log.info("Updated teacher info: firstName - {}, lastName - {}, classes - {}", teacher.getFirstName(), teacher.getLastName(), teacher.getStudyClasses());
    }

    @Override
    public void deleteUserById(String userId) {
        log.info("Deleting user with id {}", userId);
        userRepository.deleteById(userId);
        log.info("Deleted user with id - {}", userId);
    }

    @Override
    public Page<User> findAllUsersWithPagination(int pageNumber, int pageSize) {
        log.info("Searching for users with page size {} and pageSize {}", pageNumber, pageSize);
        Page<User> pageResult = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} users", pageResult.getTotalPages());
        return pageResult;
    }

    @Override
    public Page<Student> findAllStudentsWithPagination(int pageNumber, int pageSize) {
        log.info("Searching for all students");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAllStudents(pageable);
    }

    @Override
    public Page<Teacher> findAllTeachersWithPagination(int pageNumber, int pageSize) {
        log.info("Searching for all teachers");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAllTeachers(pageable);
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
