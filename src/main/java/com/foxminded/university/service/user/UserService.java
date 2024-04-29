package com.foxminded.university.service.user;

import com.foxminded.university.model.users.User;
import com.foxminded.university.model.users.dtos.StudentDTO;
import com.foxminded.university.model.users.dtos.TeacherDTO;

import java.util.List;

public interface UserService {

    void saveStudent(StudentDTO studentDTO);

    void saveTeacher(TeacherDTO teacherDTO);

    User findUserById(String userId);

    void updateStudent(StudentDTO studentDTO);

    void updateTeacher(TeacherDTO teacherDTO);

    void deleteUserById(String userId);

    List<User> findAllUsersWithPagination(int pageNumber, int pageSize);
}
