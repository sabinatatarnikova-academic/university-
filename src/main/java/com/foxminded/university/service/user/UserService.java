package com.foxminded.university.service.user;

import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import org.springframework.data.domain.Page;


public interface UserService {

    void saveStudent(StudentDTO studentDTO);

    void saveTeacher(TeacherDTO teacherDTO);

    User findUserById(String userId);

    void updateStudent(StudentDTO studentDTO);

    void updateTeacher(TeacherDTO teacherDTO);

    void deleteUserById(String userId);

    Page<User> findAllUsersWithPagination(int pageNumber, int pageSize);

    Page<Student> findAllStudentsWithPagination(int pageNumber, int pageSize);

    Page<Teacher> findAllTeachersWithPagination(int pageNumber, int pageSize);
}
